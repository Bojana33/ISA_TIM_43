package isa2.demo.Service.ServiceImpl;

import isa2.demo.Config.ModelMapperConfig;
import isa2.demo.DTO.UserComplaintDTO;
import isa2.demo.Model.*;
import isa2.demo.Repository.UserComplaintRepository;
import isa2.demo.Service.OwnerService;
import isa2.demo.Service.UserComplaintService;
import isa2.demo.Service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserComplaintServiceImpl implements UserComplaintService {

    public final ReservationServiceImpl reservationService;
    public final EntityServiceImpl entityService;
    public final UserComplaintRepository userComplaintRepository;
    public final UserService userService;
    public final OwnerService ownerService;
    private final ModelMapperConfig modelMapper;

    public UserComplaintServiceImpl(ReservationServiceImpl reservationService,
                                    EntityServiceImpl entityService,
                                    UserComplaintRepository userComplaintRepository,
                                    UserService userService,
                                    OwnerService ownerService,
                                    ModelMapperConfig modelMapperConfig){
        this.entityService = entityService;
        this.reservationService = reservationService;
        this.userComplaintRepository = userComplaintRepository;
        this.userService = userService;
        this.ownerService = ownerService;
        this.modelMapper = modelMapperConfig;
    }

    //@Override
    //public List<Reservation> createClientsList(Integer clientId) {
      //  List<Reservation> reservations = this.reservationService.findByClient(client);
        //List<Entity> entities = new ArrayList<>();
        //for (Reservation reservation : reservations) {
          //  Entity entity = this.entityService.findByReservations(reservation);
//            if (entity instanceof Adventure){
//                Owner owner = ((Adventure) entity).getOwner();
//            }
            //entities.add(entity);
        //}
        //return reservations;
    //}

    @Override
    public UserComplaint save(UserComplaint userComplaint, String username) throws Exception{
        //TO DO: check if that user has that reservation
        User existingUser = userService.findByUsername(username);
        if (userComplaint.getReservation().getClient().getId() != existingUser.getId())
            throw new Exception("This client is not allowed to give a cooment, rate or complaint for this entity");
        userComplaint.setProcessed(false);
        return this.userComplaintRepository.save(userComplaint);
    }

    @Override
    public UserComplaint update(UserComplaint userComplaint) throws Exception{
        UserComplaint userComplaintToUpdate = this.userComplaintRepository.findById(userComplaint.getId()).orElse(null);
        if (userComplaintToUpdate == null){
            throw new Exception("User complaint with this id doesn't exists");
        }
        userComplaintToUpdate.setResponse(userComplaint.getResponse());
        userComplaintToUpdate.setProcessed(userComplaint.getProcessed());
        return this.userComplaintRepository.save(userComplaintToUpdate);
    }

    @Override
    public List<UserComplaint> findAllUnprocessedComplaints() {
        return this.userComplaintRepository.findAllByProcessedIsFalse();
    }

    @Override
    public void sendResponse(UserComplaint userComplaint) {
        try {
            Client client = (Client) this.userService.findById(userComplaint.getReservation().getClient().getId());
            Entity entity = this.entityService.findById(userComplaint.getReservation().getEntity().getId());
            Owner owner = this.ownerService.findByEntity(entity);

            userComplaint.setProcessed(Boolean.TRUE);
            userComplaint = update(userComplaint);

            String subject = "User complaint";
            String content = userComplaint.getResponse();

            this.userService.sendEmail(subject,content, client.getEmail());
            this.userService.sendEmail(subject,content, owner.getEmail());

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
