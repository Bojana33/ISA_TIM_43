package isa2.demo.Repository;

import isa2.demo.Model.Client;
import isa2.demo.Model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    List<Reservation> findAllByClient(Client client);

    Collection<Reservation> findAllByEntity_Id(Integer entityId);
}
