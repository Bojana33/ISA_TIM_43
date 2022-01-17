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

    public ReservationServiceImpl(ReservationRepository reservationRepository,
                                  EntityRepository entityRepository,
                                  EntityService entityService,
                                  ModelMapperConfig modelMapperConfig,
                                  ClientRepository clientRepository,
                                  BoatService boatService,
                                  CottageService cottageService,
                                  AdventureService adventureService) {
        this.reservationRepository = reservationRepository;
        this.adventureService = adventureService;
        this.cottageService = cottageService;
        this.boatService = boatService;
        this.entityRepository = entityRepository;
        this.entityService = entityService;
        this.modelMapper = modelMapperConfig;
        this.clientRepository = clientRepository;
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
        if(!checkReservation(reservationDTO, entity, false))
            throw new InvalidReservationException("Invalid reservation input");
        Reservation reservation = new Reservation();
        reservation.setEntity(entity);
        reservation.setReservationStatus(ReservationStatus.RESERVED);
        reservation.setCreationDate(LocalDateTime.now());
        reservation.setNumberOfGuests(reservationDTO.getNumberOfGuests());
        reservation.setReservedPeriod(new Period(reservationDTO.getReservedPeriod().getStartDate(), reservationDTO.getReservedPeriod().getEndDate()));

        Collection<AdditionalService> additionalServices = new ArrayList<>();
        Double price = entity.getPricePerDay() * DAYS.between(reservationDTO.getReservedPeriod().getStartDate(), reservationDTO.getReservedPeriod().getEndDate());
        for (AdditionalServiceDTO additionalServiceDTO : reservationDTO.getAdditionalServices()) {
            price += additionalServiceDTO.getPrice();
            additionalServices.add(modelMapper.modelMapper().map(additionalServiceDTO, AdditionalService.class));
        }
        reservation.setPrice(price);
        reservation.setAdditionalServices(additionalServices);
        reservation.setClient(clientRepository.findById(reservationDTO.getClientId()).get());
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
        if(!checkReservation(reservationDTO, entity, true))
            throw new InvalidReservationException("Invalid reservation input");
        Reservation reservation = reservationRepository.getById(reservationDTO.getId());
        if (reservation.getReservationStatus() == ReservationStatus.RESERVED)
            throw new InvalidReservationException("Invalid reservation input");
        reservation.setReservationStatus(ReservationStatus.RESERVED);
        reservation.setNumberOfGuests(reservationDTO.getNumberOfGuests());
        Collection<AdditionalService> additionalServices = new ArrayList<>();
        Double price = entity.getPricePerDay() * DAYS.between(reservationDTO.getReservedPeriod().getStartDate(), reservationDTO.getReservedPeriod().getEndDate());
        for (AdditionalServiceDTO additionalServiceDTO : reservationDTO.getAdditionalServices()) {
            price += additionalServiceDTO.getPrice();
            additionalServices.add(modelMapper.modelMapper().map(additionalServiceDTO, AdditionalService.class));
        }
        //check if reservation is in sale period and calculate discount
        if (LocalDateTime.now().isAfter(reservation.getSalePeriod().getStartDate()) && LocalDateTime.now().isBefore(reservation.getSalePeriod().getEndDate()) && reservation.getDiscount() != null)
            price = price - reservation.getDiscount();
        reservation.setPrice(price);
        reservation.setAdditionalServices(additionalServices);
        reservation.setClient(clientRepository.findById(reservationDTO.getClientId()).get());
        reservation = reservationRepository.save(reservation);

        //send email
        try {
            sendEmailForReservation(reservation);
        }catch (MessagingException me) {
            return reservation;
        }

        return reservation;
    }

    private Boolean checkReservation(ReservationDTO reservationDTO, Entity entity, Boolean fastReservation){
        if (reservationDTO.getReservedPeriod().getStartDate().isBefore(LocalDateTime.now()))
            return false;
        if (reservationDTO.getReservedPeriod().getEndDate().isBefore(reservationDTO.getReservedPeriod().getStartDate()))
            return false;
        for (Reservation reservation : entity.getReservations())
            if (reservation.getReservationStatus() == ReservationStatus.RESERVED && entityService.doTimeIntervalsIntersect(reservation.getReservedPeriod().getStartDate(), reservation.getReservedPeriod().getEndDate(), reservationDTO.getReservedPeriod().getStartDate(), reservationDTO.getReservedPeriod().getEndDate()))
                return false;

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
}
