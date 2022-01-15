package isa2.demo.Service;

import isa2.demo.DTO.ReservationDTO;
import isa2.demo.Exception.InvalidReservationException;
import isa2.demo.Model.Reservation;

import java.util.Collection;

public interface ReservationService {
    Collection<Reservation> findAllReservationsForEntity(Integer entityId);
    Reservation reserveEntity(ReservationDTO reservationDTO) throws InvalidReservationException;
}
