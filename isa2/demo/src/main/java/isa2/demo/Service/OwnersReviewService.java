package isa2.demo.Service;

import isa2.demo.Model.OwnersReview;

import java.util.List;

public interface OwnersReviewService {

    OwnersReview save(OwnersReview ownersReview) throws Exception;

    OwnersReview update(OwnersReview ownersReview) throws Exception;

    List<OwnersReview> findAllByStatusOnHoldAndReportedTrue();

    void sendResponse(OwnersReview ownersReview);
}
