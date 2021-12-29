package isa2.demo.Repository;

import isa2.demo.Model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    Collection<Reservation> findAllByEntity_Id(Integer entityId);
}
