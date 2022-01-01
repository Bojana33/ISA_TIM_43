package isa2.demo.Service.ServiceImpl;

import isa2.demo.Model.*;
import isa2.demo.Repository.EntityRepository;
import isa2.demo.Service.EntityService;
import isa2.demo.Service.UserService;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.Collection;

@Service
public class EntityServiceImpl implements EntityService {
    private final EntityRepository entityRepository;
    private final UserService userService;

    public EntityServiceImpl(EntityRepository entityRepository, UserService userService) {
        this.entityRepository = entityRepository;
        this.userService = userService;
    }

    @Override
    public Entity addRentalTime(Integer entity_id, RentalTime rentalTime) {
        Entity entity =  entityRepository.getById(entity_id);
        if(isRentalTimeDateValid(entity,rentalTime)){
            Collection<RentalTime> rentalTimeList = entity.getRentalTimes();
            rentalTime.setEntity(entity);
            rentalTimeList.add( rentalTime);
            entity.setRentalTimes(rentalTimeList);
            entity = entityRepository.save(entity);
            return entity;
        }else{
            return null;
        }
    }

    @Override
    public Entity addReservation(Integer entity_id, Reservation reservation) throws MessagingException {
        Entity entity =  entityRepository.getById(entity_id);
        if(isReservationTimeValid(entity, reservation)){
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
            String email_content = "<h2>Special offer just for you!</h2><p>" + entity.getName()
                    + " will be available for reservation from:</p><p><b>" +
                    reservation.getReservedPeriod().getStartDate() + "</b> to <b>" +
                    reservation.getReservedPeriod().getEndDate() + "</b></p>" +
                    "<p>This offer last from:</p><p><b>" +
                    reservation.getSalePeriod().getStartDate() + "</b> to <b>" +
                    reservation.getSalePeriod().getEndDate() + "</b></p>";
            for(User user: entity.getSubscribedClients()){
                userService.sendEmail("Special offer", email_content, user.getEmail());
            }
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
    public boolean isReservationTimeValid(Entity entity, Reservation reservation) {
        Collection<Reservation> reservationCollection = entity.getReservations();
        for (Reservation reservationTemp : reservationCollection) {
            if (doTimeIntervalsIntersect(reservation.getReservedPeriod().getStartDate(), reservation.getReservedPeriod().getEndDate(),
                    reservationTemp.getReservedPeriod().getStartDate(), reservationTemp.getReservedPeriod().getEndDate()))
                return false;
        }
        return true;
    }

    @Override
    public boolean doTimeIntervalsIntersect(LocalDateTime startDate1, LocalDateTime endDate1,LocalDateTime startDate2, LocalDateTime endDate2) {
        return (!startDate2.isAfter(endDate1) && !endDate2.isBefore(startDate1));
    }


}
