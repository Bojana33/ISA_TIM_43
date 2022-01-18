package isa2.demo.Service;

import isa2.demo.Model.Client;
import isa2.demo.DTO.ReservationDTO;
import isa2.demo.Exception.InvalidReservationException;
import isa2.demo.Model.Owner;
import isa2.demo.Model.Period;
import isa2.demo.Model.Reservation;
import java.util.Collection;
import java.util.Optional;

import java.util.*;

public interface ReservationService {

    List<Reservation> findByClient(Client client);

    Reservation reserveEntity(ReservationDTO reservationDTO) throws InvalidReservationException;
//    Collection<Reservation> findAllReservationsForEntity(Integer entityId);

    Collection<Reservation> findAllReservationsForOwner(Owner owner);

    Collection<Reservation> findAllReservationsForEntity(Integer entityId, Optional<Period> periodOptional);
    Collection<Reservation> findAllFutureReservationsOnSale();
    Reservation fastReservation(ReservationDTO reservationDTO) throws  InvalidReservationException;
    List<Reservation> findAllReservationsForClient(Integer clientId);
    void cancelReservation(String username, Integer reservationId) throws Exception;
}
