package isa2.demo.Service;

import isa2.demo.Model.Owner;
import isa2.demo.Model.Period;
import isa2.demo.Model.Reservation;

import java.util.Collection;
import java.util.Optional;

public interface ReservationService {
//    Collection<Reservation> findAllReservationsForEntity(Integer entityId);

    Collection<Reservation> findAllReservationsForInstructor(Owner owner);

    Collection<Reservation> findAllReservationsForEntity(Integer entityId, Optional<Period> periodOptional);

}
