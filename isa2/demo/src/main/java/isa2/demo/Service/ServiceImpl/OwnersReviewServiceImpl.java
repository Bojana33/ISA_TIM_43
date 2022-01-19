package isa2.demo.Service.ServiceImpl;

import isa2.demo.Model.*;
import isa2.demo.Repository.OwnersReviewRepository;
import isa2.demo.Service.EntityService;
import isa2.demo.Service.OwnerService;
import isa2.demo.Service.OwnersReviewService;
import isa2.demo.Service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OwnersReviewServiceImpl implements OwnersReviewService {
    
    private final OwnersReviewRepository ownersReviewRepository;
    private final EntityService entityService;
    private final OwnerService ownerService;
    private final UserService userService;
    
    public OwnersReviewServiceImpl(OwnersReviewRepository ownersReviewRepository, EntityService entityService, OwnerService ownerService, UserService userService){
        this.ownersReviewRepository = ownersReviewRepository;
        this.entityService = entityService;
        this.ownerService = ownerService;
        this.userService = userService;
    }
    
    @Override
    public OwnersReview save(OwnersReview ownersReview) throws Exception {
        ownersReview.setReviewStatus(ReviewStatus.ONHOLD);
        Client client = (Client) this.userService.findById(ownersReview.getReservation().getClient().getId());
        if (ownersReview.getAppeared() == Boolean.FALSE){
            client.setPenalty(client.getPenalty() + 1);
            this.userService.save(client);
        }
        return this.ownersReviewRepository.save(ownersReview);
    }

    @Override
    public OwnersReview update(OwnersReview ownersReview) throws Exception {
        OwnersReview ownersReviewToUpdate = this.ownersReviewRepository.findById(ownersReview.getId()).orElse(null);
        if (ownersReviewToUpdate == null){
            throw new Exception("Owner review with this id doesn't exists");
        }
        ownersReviewToUpdate.setReviewStatus(ownersReview.getReviewStatus());
        return this.ownersReviewRepository.save(ownersReviewToUpdate);
    }

    @Override
    public List<OwnersReview> findAllByStatusOnHoldAndReportedTrue() {
        return this.ownersReviewRepository.findAllByIsReportedTrueAndReviewStatus(ReviewStatus.ONHOLD);
    }

    @Override
    public void sendResponse(OwnersReview ownersReview) {
        try {
            Client client = (Client) this.userService.findById(ownersReview.getReservation().getClient().getId());
            Entity entity = this.entityService.findById(ownersReview.getReservation().getEntity().getId());
            Owner owner = this.ownerService.findByEntity(entity);

            ownersReview = update(ownersReview);

            String subject = "Owner review";
            String content;

            if(ownersReview.getReviewStatus() == ReviewStatus.INAPPROPRIATE){
                content = client.getFirstName() + " " + client.getSurname() + " has earned one penalty.";
                client.setPenalty(client.getPenalty() + 1);
                this.userService.save(client);
            }else{
                content = client.getFirstName() + " " + client.getSurname() + " has NOT earned penalty.";
            }
            this.userService.sendEmail(subject,content, owner.getEmail());


        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
