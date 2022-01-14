package isa2.demo.Service;

import isa2.demo.Model.Client;
import isa2.demo.Model.Reservation;
import java.util.Collection;
import java.util.Optional;
import isa2.demo.Model.Owner;
import isa2.demo.Model.Period;

import java.util.*;

public interface ReservationService {

    List<Reservation> findByClient(Client client);

//    Collection<Reservation> findAllReservationsForEntity(Integer entityId);

    Collection<Reservation> findAllReservationsForOwner(Owner owner);

    Collection<Reservation> findAllReservationsForEntity(Integer entityId, Optional<Period> periodOptional);

}
