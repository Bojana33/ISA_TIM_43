package isa2.demo.Repository;

import isa2.demo.Model.ClientsReview;
import isa2.demo.Model.ClientsReviewStatus;
import isa2.demo.Model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientsReviewRepository extends JpaRepository<ClientsReview, Integer> {

    List<ClientsReview> findAllByStatus(ClientsReviewStatus clientsReviewStatus);
    boolean existsClientsReviewsByReservation(Reservation reservation);
    ClientsReview findClientsReviewByReservation(Reservation reservation);
}
