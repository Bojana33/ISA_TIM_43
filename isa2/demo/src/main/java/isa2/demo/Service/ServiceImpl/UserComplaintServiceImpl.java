package isa2.demo.Service.ServiceImpl;

import isa2.demo.Model.*;
import isa2.demo.Repository.UserComplaintRepository;
import isa2.demo.Service.UserComplaintService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserComplaintServiceImpl implements UserComplaintService {

    public final ReservationServiceImpl reservationService;

    public final EntityServiceImpl entityService;

    public final UserComplaintRepository userComplaintRepository;

    public UserComplaintServiceImpl(ReservationServiceImpl reservationService, EntityServiceImpl entityService,UserComplaintRepository userComplaintRepository){
        this.entityService = entityService;
        this.reservationService = reservationService;
        this.userComplaintRepository = userComplaintRepository;
    }

    @Override
    public List<Reservation> createClientsList(Client client) {
        List<Reservation> reservations = this.reservationService.findByClient(client);
        List<Entity> entities = new ArrayList<>();
        for (Reservation reservation : reservations) {
            Entity entity = this.entityService.findByReservations(reservation);
//            if (entity instanceof Adventure){
//                Owner owner = ((Adventure) entity).getOwner();
//            }
            entities.add(entity);
        }
        return reservations;
    }

    @Override
    public UserComplaint save(UserComplaint userComplaint) {
        return this.userComplaintRepository.save(userComplaint);
    }

    @Override
    public UserComplaint update(UserComplaint userComplaint) throws Exception{
        UserComplaint userComplaintToUpdate = this.userComplaintRepository.findById(userComplaint.getId()).orElse(null);
        if (userComplaintToUpdate == null){
            throw new Exception("User complaint with this id doesn't exists");
        }
        userComplaintToUpdate.setResponse(userComplaint.getResponse());
        return this.userComplaintRepository.save(userComplaintToUpdate);
    }
}
