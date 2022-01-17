package isa2.demo.Service.ServiceImpl;

import isa2.demo.Model.ClientsReview;
import isa2.demo.Model.ClientsReviewStatus;
import isa2.demo.Repository.ClientsReviewRepository;
import isa2.demo.Service.ClientsReviewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientsReviewServiceImpl implements ClientsReviewService {

    private final ClientsReviewRepository clientsReviewRepository;

    public ClientsReviewServiceImpl(ClientsReviewRepository clientsReviewRepository){
        this.clientsReviewRepository = clientsReviewRepository;
    }

    @Override
    public ClientsReview save(ClientsReview clientsReview) {
        clientsReview.setStatus(ClientsReviewStatus.UNPROCESSED);
        return this.clientsReviewRepository.save(clientsReview);
    }

    @Override
    public ClientsReview update(ClientsReview clientsReview) throws Exception {
        ClientsReview clientsReviewToUpdate = this.clientsReviewRepository.findById(clientsReview.getId()).orElse(null);
        if (clientsReviewToUpdate == null){
            throw new Exception("User complaint with this id doesn't exists");
        }
        clientsReviewToUpdate.setStatus(clientsReview.getStatus());
        return this.clientsReviewRepository.save(clientsReviewToUpdate);
    }

    @Override
    public List<ClientsReview> findAllUnprocessedReviews() {
        return this.clientsReviewRepository.findAllByStatus(ClientsReviewStatus.UNPROCESSED);
    }
}
