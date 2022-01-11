package isa2.demo.Repository;

import isa2.demo.Model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    Collection<Reservation> findAllByEntity_Id(Integer entityId);
    Collection<Reservation> findAllByEntity_IdAndReservedPeriod_StartDateAfterAndReservedPeriod_EndDateBefore(Integer entity_id, LocalDateTime reservedPeriod_startDate, LocalDateTime reservedPeriod_endDate);
    Collection<Reservation> findAllByEntity_idAndClientsReviewNotNull(Integer entityId);
}
