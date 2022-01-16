package isa2.demo.Repository;

import isa2.demo.Model.ClientsReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientsReviewRepository extends JpaRepository<ClientsReview, Integer> {

    List<ClientsReview> findAllByProcessedIsFalse();
}
