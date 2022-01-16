package isa2.demo.Service.ServiceImpl;

import isa2.demo.Config.ModelMapperConfig;
import isa2.demo.DTO.AdditionalServiceDTO;
import isa2.demo.DTO.FreeEntityDTO;
import isa2.demo.DTO.ReservationDTO;
import isa2.demo.Exception.EmailAlreadyInUseException;
import isa2.demo.Exception.InvalidReservationException;
import isa2.demo.Model.*;
import isa2.demo.Repository.ClientRepository;
import isa2.demo.Repository.EntityRepository;
import isa2.demo.Repository.ReservationRepository;
import isa2.demo.Repository.PeriodRepository;
import isa2.demo.Repository.ReservationRepository;
import isa2.demo.Service.ClientService;
import isa2.demo.Service.EntityService;
import isa2.demo.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class EntityServiceImpl implements EntityService {
    private final EntityRepository entityRepository;
    private final UserService userService;
    private final ReservationRepository reservationRepository;
    private final ClientService clientService;
    private final ModelMapperConfig modelMapper;
    private final ClientRepository clientRepository;
    private final PeriodRepository periodRepository;

    public EntityServiceImpl(EntityRepository entityRepository, UserService userService, ClientService clientService,
                             ReservationRepository reservationRepository, ModelMapperConfig modelMapperConfig,
                             ClientRepository clientRepository, PeriodRepository periodRepository) {
        this.entityRepository = entityRepository;
        this.userService = userService;
        this.clientService = clientService;
        this.reservationRepository = reservationRepository;
        this.modelMapper = modelMapperConfig;
        this.clientRepository = clientRepository;
        this.periodRepository = periodRepository;
    }

    @Override
    public Entity addRentalTime(Integer entity_id, RentalTime rentalTime) throws MessagingException {
        Entity entity =  entityRepository.getById(entity_id);
        if(isRentalTimeDateValid(entity,rentalTime)){
            Collection<RentalTime> rentalTimeList = entity.getRentalTimes();
            rentalTime.setEntity(entity);
            rentalTimeList.add( rentalTime);
            entity.setRentalTimes(rentalTimeList);
            entity = entityRepository.save(entity);
            sendEmailsToSubscribers(entity ,rentalTime.getStart_date(),rentalTime.getEnd_date(),Optional.empty(),Optional.empty());
            return entity;
        }else{
            return null;
        }
    }

    @Override
    public Entity addReservation(Integer entity_id, Reservation reservation) throws MessagingException {
        //TODO: ako imas vremena, resi ovo da bude sa optional
        Entity entity =  entityRepository.findById(entity_id).get();
        if(isReservationOverlaping(entity, reservation)){
            Collection<Reservation> reservations = entity.getReservations();
            reservation.setCreationDate(LocalDateTime.now());
            reservation.setEntity(entity);
            reservation.setReservationStatus(ReservationStatus.FREE);
            Collection<AdditionalService> additionalServices = reservation.getAdditionalServices();
            if(additionalServices != null){
                for(AdditionalService additionalService: additionalServices){
                    additionalService.setReservation(reservation);
                    additionalService.setEntity(entity);
                }
                reservation.setAdditionalServices(additionalServices);
            }
            reservations.add( reservation);
            entity.setReservations(reservations);
            entity = entityRepository.save(entity);
            //TODO: posalji mejlove subscribovanim klijentima
            sendEmailsToSubscribers(entity,reservation.getReservedPeriod().getStartDate(), reservation.getReservedPeriod().getEndDate(),
                                    Optional.of(reservation.getSalePeriod().getStartDate()), Optional.of(reservation.getSalePeriod().getEndDate()));
            return entity;
        }
        return null;
    }

    @Override
    public boolean isRentalTimeDateValid(Entity entity, RentalTime rentalTime) {

        Collection<RentalTime> rentalTimeCollection = entity.getRentalTimes();
        for(RentalTime rentalTimeTemp: rentalTimeCollection){
            if (doTimeIntervalsIntersect(rentalTime.getStart_date(),rentalTime.getEnd_date(), rentalTimeTemp.getStart_date(), rentalTimeTemp.getEnd_date()))
                return false;
        }
        return true;
    }

    @Override
    public boolean isReservationOverlaping(Entity entity, Reservation reservation) {
        Collection<Reservation> reservationCollection = entity.getReservations();
        if (isReservationTimeInvalid(reservation)) return false;
        for (Reservation reservationTemp : reservationCollection) {
            if (doTimeIntervalsIntersect(reservation.getReservedPeriod().getStartDate(), reservation.getReservedPeriod().getEndDate(),
                    reservationTemp.getReservedPeriod().getStartDate(), reservationTemp.getReservedPeriod().getEndDate()))
                return false;
        }
        return true;
    }

    @Override
    public boolean isReservationTimeInvalid(Reservation reservation) {
        return reservation.getReservedPeriod().getStartDate().isAfter(reservation.getReservedPeriod().getEndDate()) ||
                reservation.getReservedPeriod().getStartDate().isEqual(reservation.getReservedPeriod().getEndDate()) ||
                reservation.getSalePeriod().getStartDate().isAfter(reservation.getSalePeriod().getEndDate()) ||
                reservation.getSalePeriod().getStartDate().isEqual(reservation.getSalePeriod().getEndDate());
    }

    @Override
    public boolean doTimeIntervalsIntersect(LocalDateTime startDate1, LocalDateTime endDate1,LocalDateTime startDate2, LocalDateTime endDate2) {
        return (!startDate2.isAfter(endDate1) && !endDate2.isBefore(startDate1));
    }

    @Override
    public Entity findByReservations(Reservation reservation) {
        return this.entityRepository.findByReservations(reservation);
    }


    private void sendEmailsToSubscribers(Entity entity,
                                         LocalDateTime offerStartDate,
                                         LocalDateTime offerEndDate,
                                         Optional<LocalDateTime> saleStartDate,
                                         Optional<LocalDateTime> saleEndDate) throws MessagingException {

        String offerDuration = "<h2>Special offer just for you!</h2><p>" + entity.getName()
                + " will be available for reservation from:</p><p><b>" +
                offerStartDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "</b> to <b>" +
                offerEndDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "</b></p>";

        if(saleStartDate.isPresent() && saleEndDate.isPresent()){
            offerDuration = offerDuration.concat("<p>This offer last from:</p><p><b>" + saleStartDate.get().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                    + "</b> to <b>" + saleEndDate.get().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "</b></p>");
        }

        for(User user: entity.getSubscribedClients()){
            userService.sendEmail("Special offer", offerDuration, user.getEmail());
        }
    }

    @Override
    public Double findAverageGrade(Integer entity_id) {
        Optional<Entity> entity = entityRepository.findById(entity_id);
        Double avgGrade = 0.0;
        if(entity.isPresent()){
            Collection<Reservation> reservations= reservationRepository.findAllByEntity_idAndClientsReviewNotNull(entity_id);
            if(!reservations.isEmpty()){
                for(Reservation reservation: reservations){
                    avgGrade += reservation.getClientsReview().getGrade();
                }
                avgGrade =  avgGrade/reservations.size();
            }
        }
        return avgGrade;
    }

    @Override
    public Entity findById(Integer id) {
        return this.entityRepository.findById(id).orElse(null);
    }

    @Override
    public void uploadEntityPhoto(Integer id, String fileName) {
        Entity entity = entityRepository.findById(id).get();
        entity.setEntityPhoto(fileName);
        entityRepository.save(entity);
    }

    private void sendEmailForReservation(Reservation reservation) throws MessagingException{
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
    public boolean isPeriodInRentalTime(Entity entity, LocalDateTime startDate, LocalDateTime endDate) {
        Collection<RentalTime> rentalTimes = entity.getRentalTimes();
        for (RentalTime rentalTime : rentalTimes) {
            if(rentalTime.getStart_date().isBefore(startDate) && rentalTime.getEnd_date().isAfter(endDate))
                return true;
        }
        return false;
    }

}
