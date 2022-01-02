package isa2.demo.Service;

import isa2.demo.Model.Client;
import isa2.demo.Model.Reservation;

import java.util.*;

public interface ReservationService {

    List<Reservation> findByClient(Client client);

    Collection<Reservation> findAllReservationsForEntity(Integer entityId);
}
