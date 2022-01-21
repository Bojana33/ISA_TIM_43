package isa2.demo.Service.ServiceImpl;

import isa2.demo.Config.ModelMapperConfig;
import isa2.demo.DTO.AdditionalServiceDTO;
import isa2.demo.DTO.ReservationDTO;
import isa2.demo.Exception.InvalidReservationException;
import isa2.demo.Model.*;
import isa2.demo.Repository.ClientRepository;
import isa2.demo.Repository.EntityRepository;
import isa2.demo.Repository.ReservationRepository;
import isa2.demo.Service.*;
import org.springframework.stereotype.Service;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final EntityRepository entityRepository;
    private final EntityService entityService;
    private final ModelMapperConfig modelMapper;
    private final ClientRepository clientRepository;
    private final AdventureService adventureService;
    private final CottageService cottageService;
    private final BoatService boatService;
    private final ClientService clientService;
    private final ConfigSingletonService configSingletonService;
    private final OwnerService ownerService;

    public ReservationServiceImpl(ReservationRepository reservationRepository,
                                  EntityRepository entityRepository,
                                  EntityService entityService,
                                  ModelMapperConfig modelMapperConfig,
                                  ClientRepository clientRepository,
                                  BoatService boatService,
                                  CottageService cottageService,
                                  AdventureService adventureService,
                                  ClientService clientService,
                                  ConfigSingletonService configSingletonService,
                                  OwnerService ownerService) {
        this.reservationRepository = reservationRepository;
        this.adventureService = adventureService;
        this.cottageService = cottageService;
        this.boatService = boatService;
        this.entityRepository = entityRepository;
        this.entityService = entityService;
        this.modelMapper = modelMapperConfig;
        this.clientRepository = clientRepository;
        this.clientService = clientService;
        this.configSingletonService = configSingletonService;
        this.ownerService = ownerService;
    }

    @Override
    public Collection<Reservation> findAllReservationsForEntity(Integer entityId, Optional<Period> period) {
        Collection<Reservation> reservations = new ArrayList<>();
        if(period.isPresent()){
            reservations = reservationRepository.findAllByEntity_IdAndReservedPeriod_StartDateAfterAndReservedPeriod_EndDateBefore(entityId,period.get().getStartDate(),period.get().getEndDate());
        }else{
            reservations = reservationRepository.findAllByEntity_Id(entityId);
        }
        return reservations;
    }

    @Override
    public List<Reservation> findByClient(Client client) {
        return this.reservationRepository.findAllByClient(client);
    }


    @Override
    public Collection<Reservation> findAllReservationsForOwner(Owner owner) {
        Collection<Adventure> adventures = this.adventureService.findAdventuresByInstructor(owner);
        Collection<Cottage> cottages = this.cottageService.findCottagesByOwner(owner);
        Collection<Boat> boats = this.boatService.findBoatsByOwner(owner);
        Collection<Reservation> reservations = new HashSet<>();
        if (owner.getOwnerType() == OwnerType.INSTRUCTOR) {
            for (Adventure adventure : adventures) {
                Collection<Reservation> reservationsForAdventure = findAllReservationsForEntity(adventure.getId(), Optional.empty());
                reservations.addAll(reservationsForAdventure);
            }
        }
        if (owner.getOwnerType() == OwnerType.COTTAGEOWNER) {
            for (Cottage cottage : cottages) {
                Collection<Reservation> reservationsForCottage = findAllReservationsForEntity(cottage.getId(), Optional.empty());
                reservations.addAll(reservationsForCottage);
            }
        }
        if (owner.getOwnerType() == OwnerType.BOATOWNER) {
            for (Boat boat : boats) {
                Collection<Reservation> reservationsForBoat = findAllReservationsForEntity(boat.getId(), Optional.empty());
                reservations.addAll(reservationsForBoat);
            }
        }
        return reservations;
    }

    @Override
    public Reservation reserveEntity(ReservationDTO reservationDTO) throws InvalidReservationException {
        Entity entity = entityRepository.findById(reservationDTO.getEntityId()).get();
        Client client = clientRepository.findById(reservationDTO.getClientId()).get();
        if(client.getPenalty() >= 3)
            throw new InvalidReservationException("Client has 3 or more penalties");
        if(!checkReservation(reservationDTO, entity, false))
            throw new InvalidReservationException("Invalid reservation input");
        //check if client canceled same reservation
        for (Reservation reservation : client.getReservation()){
            if(reservation.getEntity().equals(entity) && reservation.getReservationStatus() == ReservationStatus.CANCELED &&
                    reservation.getReservedPeriod().getStartDate().isEqual(reservationDTO.getReservedPeriod().getStartDate()) &&
                    reservation.getReservedPeriod().getEndDate().isEqual(reservationDTO.getReservedPeriod().getEndDate()))
                throw new InvalidReservationException("Client canceled this reservation, can't reserve one more time");
        }
        Reservation reservation = saveReservation(reservationDTO, entity, client);
        Collection<AdditionalService> additionalServices = new ArrayList<>();
        Double price = entity.getPricePerDay() * DAYS.between(reservationDTO.getReservedPeriod().getStartDate(), reservationDTO.getReservedPeriod().getEndDate());
        for (AdditionalServiceDTO additionalServiceDTO : reservationDTO.getAdditionalServices()) {
            price += additionalServiceDTO.getPrice();
            additionalServices.add(modelMapper.modelMapper().map(additionalServiceDTO, AdditionalService.class));
        }
        this.configSingletonService.addReservationPointsToClient(client);
        Double clientsDiscount = this.configSingletonService.getClientDiscount(client);
        if (clientsDiscount != -1){
            price = price * (1 - clientsDiscount);
        }
        // calculate owner income for this reservation and his loyalty points
        Owner owner = this.ownerService.findByEntity(entity);
        this.configSingletonService.addReservationPointsToOwner(owner);
        Double ownerIncome = this.configSingletonService.getOwnerIncome(owner);
        ownerIncome = price * ownerIncome;
        reservation.setOwnersIncome(ownerIncome);

        reservation.setPrice(price);
        reservation.setAdditionalServices(additionalServices);
        reservation.setClient(client);
        Collection<Reservation> entityReservations = entity.getReservations();
        entityReservations.add(reservation);
        entity.setReservations(entityReservations);
        reservation = reservationRepository.save(reservation);

        //send email
        try {
            sendEmailForReservation(reservation);
        }catch (MessagingException me) {
            return reservation;
        }
        return reservation;
    }

    @Override
    public Reservation fastReservation(ReservationDTO reservationDTO) throws  InvalidReservationException{
        Entity entity = entityRepository.findById(reservationDTO.getEntityId()).get();
        Client client = clientRepository.findById(reservationDTO.getClientId()).get();
        if(client.getPenalty() >= 3)
            throw new InvalidReservationException("Client has 3 or more penalties");
        if(!checkReservation(reservationDTO, entity, true))
            throw new InvalidReservationException("Invalid reservation input");
        Reservation reservation = reservationRepository.getById(reservationDTO.getId());
        if (client.getReservation().contains(reservation) && reservation.getReservationStatus() == ReservationStatus.CANCELED)
            throw new InvalidReservationException("Client canceled this reservation, can't reserve one more time");
        if (reservation.getReservationStatus() == ReservationStatus.RESERVED)
            throw new InvalidReservationException("Invalid reservation input");
        Collection<AdditionalService> additionalServices = new ArrayList<>();

        Double clientsDiscount = this.configSingletonService.getClientDiscount(client);
        Double price = entity.getPricePerDay() * DAYS.between(reservationDTO.getReservedPeriod().getStartDate(), reservationDTO.getReservedPeriod().getEndDate());

        // calculate clients discount based on category
        if (clientsDiscount != -1){
            price = price * (1 - clientsDiscount);
        }
        this.configSingletonService.addReservationPointsToClient(client);

        for (AdditionalServiceDTO additionalServiceDTO : reservationDTO.getAdditionalServices()) {
            price += additionalServiceDTO.getPrice();
            additionalServices.add(modelMapper.modelMapper().map(additionalServiceDTO, AdditionalService.class));
        }
        //check if reservation is in sale period and calculate discount
        if (LocalDateTime.now().isAfter(reservation.getSalePeriod().getStartDate()) && LocalDateTime.now().isBefore(reservation.getSalePeriod().getEndDate()) && reservation.getDiscount() != null)
            price = price - reservation.getDiscount();

        // calculate owner income for this reservation
        Owner owner = this.ownerService.findByEntity(entity);
        this.configSingletonService.addReservationPointsToOwner(owner);
        Double ownerIncome = this.configSingletonService.getOwnerIncome(owner);
        ownerIncome = price * ownerIncome;
        reservation.setOwnersIncome(ownerIncome);

        reservation.setPrice(price);
        reservation.setAdditionalServices(additionalServices);
        reservation.setClient(client);
        Collection<Reservation> entityReservations = entity.getReservations();
        entityReservations.add(reservation);
        entity.setReservations(entityReservations);
        reservation = reservationRepository.save(reservation);

        //send email
        try {
            sendEmailForReservation(reservation);
        }catch (MessagingException me) {
            return reservation;
        }

        return reservation;
    }

    private Reservation saveReservation(ReservationDTO reservationDTO, Entity entity, Client client) {
        Reservation reservation = new Reservation();
        reservation.setEntity(entity);
        reservation.setReservationStatus(ReservationStatus.RESERVED);
        reservation.setCreationDate(LocalDateTime.now());
        reservation.setNumberOfGuests(reservationDTO.getNumberOfGuests());
        reservation.setReservedPeriod(new Period(reservationDTO.getReservedPeriod().getStartDate(), reservationDTO.getReservedPeriod().getEndDate()));
        reservation.setClient(client);
        return reservation;
    }

    private Boolean checkReservation(ReservationDTO reservationDTO, Entity entity, Boolean fastReservation){
        //check reservation dates
        if (reservationDTO.getReservedPeriod().getStartDate().isBefore(LocalDateTime.now()))
            return false;
        if (reservationDTO.getReservedPeriod().getEndDate().isBefore(reservationDTO.getReservedPeriod().getStartDate()))
            return false;
        //check if there is any reservation with status resrved in this period
        for (Reservation reservation : entity.getReservations())
            if (reservation.getReservationStatus() == ReservationStatus.RESERVED && entityService.doTimeIntervalsIntersect(reservation.getReservedPeriod().getStartDate(), reservation.getReservedPeriod().getEndDate(), reservationDTO.getReservedPeriod().getStartDate(), reservationDTO.getReservedPeriod().getEndDate()))
                return false;
        //check if client already canceled this reservation
        //if (clientRepository.findById(reservationDTO.getClientId()).get().getReservation().contains())
        //check if reservation reserved period is in rental time
        if(!fastReservation)
            if (!entityService.isPeriodInRentalTime(entity, reservationDTO.getReservedPeriod().getStartDate(), reservationDTO.getReservedPeriod().getEndDate()))
                return false;
        if (reservationDTO.getNumberOfGuests() < 1 || reservationDTO.getNumberOfGuests() > entity.getMaxNumberOfGuests())
            return false;
        return true;
    }

    @Override
    public Collection<Reservation> findAllFutureReservationsOnSale() {
        Collection<Reservation> reservations = reservationRepository.findAllBySalePeriod_EndDateAfter(LocalDateTime.now());
        Collection<Reservation> reservationsToReturn = new ArrayList<Reservation>();
        for (Reservation reservation : reservations)
            if (reservation.getReservationStatus() == ReservationStatus.FREE) //&& reservation.getReservedPeriod() == null)
                reservationsToReturn.add(reservation);
        return reservationsToReturn;
    }

    private void sendEmailForReservation(Reservation reservation) throws MessagingException {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("inisatim43@gmail.com", "bajicb182075");
                }
            });

            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("inisatim43@gmail.com", false));

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(reservation.getClient().getEmail()));
            msg.setSubject("Reservation!");

            String subject = "You’re booked! Pack your bags – see you soon";
            String content = "Dear " + reservation.getClient().getFirstName() + ",<br>"
                    + "You have successfully booked " + reservation.getEntity().getName() + " , from " + reservation.getReservedPeriod().getStartDate() + " untill " + reservation.getReservedPeriod().getEndDate()
                    + " .Price of this reservation is " + reservation.getPrice() + ".";

            msg.setSubject(subject);
            msg.setContent(content, "text/html");
            Transport.send(msg);
        } catch (MessagingException me) {
            System.out.println("Message exception");
        }
    }

    @Override
    public List<Reservation> findAllReservationsForClient(Integer clientId) {
        Client client  = clientRepository.getById(clientId);
        List<Reservation> reservations = reservationRepository.findAllByClient(client);
        List<Entity> entities = new ArrayList<>();
        for (Reservation reservation : reservations) {
        Entity entity = this.entityService.findByReservations(reservation);
//            if (entity instanceof Adventure){
//                Owner owner = ((Adventure) entity).getOwner();
//            }
        entities.add(entity);
        }
        return reservations;
    }

    @Override
    public void cancelReservation(String username, Integer reservationId) throws Exception{
        Client client = clientService.findByUsername(username);
        Reservation reservation = reservationRepository.getById(reservationId);
        if(!client.getReservation().contains(reservation))
            throw new Exception("Client does not have this reservation");
        if(reservation.getReservedPeriod().getStartDate().isBefore(LocalDateTime.now().plusDays(3)))
            throw new Exception("the reservation can no longer be canceled");
        reservation.setReservationStatus(ReservationStatus.CANCELED);
        reservationRepository.save(reservation);
    }
}
