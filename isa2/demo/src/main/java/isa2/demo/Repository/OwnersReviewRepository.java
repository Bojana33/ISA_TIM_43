package isa2.demo.Repository;

import isa2.demo.Model.OwnersReview;
import isa2.demo.Model.ReviewStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface OwnersReviewRepository extends JpaRepository<OwnersReview, Integer> {

    List<OwnersReview> findAllByIsReportedTrueAndReviewStatus(ReviewStatus reviewStatus);
}
