package isa2.demo.Service;

import isa2.demo.DTO.RentalTimeDTO;
import isa2.demo.Model.Entity;
import isa2.demo.Model.RentalTime;
import isa2.demo.Model.Reservation;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.Collection;

public interface EntityService {
    Entity addRentalTime(Integer entity_id, RentalTime rentalTime);
    Entity addReservation(Integer entity_id, Reservation reservation) throws MessagingException;

    boolean isReservationTimeInvalid(Reservation reservation);
    boolean isRentalTimeDateValid(Entity entity, RentalTime rentalTime);
    boolean isReservationOverlaping(Entity entity, Reservation reservation);
    boolean doTimeIntervalsIntersect(LocalDateTime startDate1, LocalDateTime endDate1,LocalDateTime startDate2, LocalDateTime endDate2);
}
