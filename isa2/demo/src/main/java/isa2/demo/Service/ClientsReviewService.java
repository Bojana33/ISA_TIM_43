package isa2.demo.Service;

import isa2.demo.Model.ClientsReview;

import java.util.List;

public interface ClientsReviewService {

    ClientsReview save(ClientsReview clientsReview) throws Exception;

    ClientsReview update(ClientsReview clientsReview) throws Exception;

    List<ClientsReview> findAllUnprocessedReviews();

    void sendResponse(ClientsReview clientsReview);
}
