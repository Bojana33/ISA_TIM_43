package isa2.demo.Service.ServiceImpl;

import isa2.demo.Model.*;
import isa2.demo.Repository.ClientsReviewRepository;
import isa2.demo.Service.ClientsReviewService;
import isa2.demo.Service.EntityService;
import isa2.demo.Service.OwnerService;
import isa2.demo.Service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientsReviewServiceImpl implements ClientsReviewService {

    private final ClientsReviewRepository clientsReviewRepository;
    private final UserService userService;
    private final EntityService entityService;
    private final OwnerService ownerService;

    public ClientsReviewServiceImpl(ClientsReviewRepository clientsReviewRepository, UserService userService, EntityService entityService, OwnerService ownerService){
        this.clientsReviewRepository = clientsReviewRepository;
        this.userService = userService;
        this.entityService = entityService;
        this.ownerService = ownerService;
    }

    @Override
    public ClientsReview save(ClientsReview clientsReview, String username) throws Exception {
        User existingUser = userService.findByUsername(username);
        Reservation reservation = clientsReview.getReservation();
        if (clientsReview.getGrade() <= 0)
            throw new Exception("Invalid input");
        if(clientsReviewRepository.existsClientsReviewsByReservation(clientsReview.getReservation()))
            throw new Exception("Invalid input");
        if (reservation.getClient().getId() != existingUser.getId())
            throw new Exception("This client is not allowed to give a comment, rate or complaint for this entity");
        clientsReview.setStatus(ClientsReviewStatus.UNPROCESSED);
        return this.clientsReviewRepository.save(clientsReview);
    }

    @Override
    public ClientsReview update(ClientsReview clientsReview) throws Exception {
        ClientsReview clientsReviewToUpdate = this.clientsReviewRepository.findById(clientsReview.getId()).orElse(null);
        if (clientsReviewToUpdate == null){
            throw new Exception("Client review with this id doesn't exists");
        }
        clientsReviewToUpdate.setStatus(clientsReview.getStatus());
        return this.clientsReviewRepository.save(clientsReviewToUpdate);
    }

    @Override
    public List<ClientsReview> findAllUnprocessedReviews() {
        return this.clientsReviewRepository.findAllByStatus(ClientsReviewStatus.UNPROCESSED);
    }

    @Override
    public void sendResponse(ClientsReview clientsReview) {
        try {
            Entity entity = this.entityService.findById(clientsReview.getReservation().getEntity().getId());
            Owner owner = this.ownerService.findByEntity(entity);

            clientsReview = update(clientsReview);

            String subject = "Client review";
            String content;

            if(clientsReview.getStatus() == ClientsReviewStatus.PUBLIC){
                content = "New review about you is public.";
                this.userService.sendEmail(subject,content, owner.getEmail());
            }


        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
