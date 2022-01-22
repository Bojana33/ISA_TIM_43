package isa2.demo.Repository;

import isa2.demo.Model.Client;
import isa2.demo.Model.Reservation;
import isa2.demo.Model.UserComplaint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.time.LocalDateTime;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    List<Reservation> findAllByClient(Client client);
    List<Reservation> findAllByClient_Id(Integer id);
    Collection<Reservation> findAllByEntity_Id(Integer entityId);
    Collection<Reservation> findAllByEntity_IdAndReservedPeriod_StartDateAfterAndReservedPeriod_EndDateBefore(Integer entity_id, LocalDateTime reservedPeriod_startDate, LocalDateTime reservedPeriod_endDate);
    Collection<Reservation> findAllByEntity_idAndClientsReviewNotNull(Integer entityId);

    Reservation findByUserComplaint(UserComplaint userComplaint);
    Collection<Reservation> findAllBySalePeriod_EndDateAfter(LocalDateTime salePeriod_startDate);
}
