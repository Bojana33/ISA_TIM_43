package isa2.demo.Service.ServiceImpl;

import isa2.demo.Model.*;
import isa2.demo.Service.UserComplaintService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserComplaintServiceImpl implements UserComplaintService {

    public final ReservationServiceImpl reservationService;

    public final EntityServiceImpl entityService;

    public UserComplaintServiceImpl(ReservationServiceImpl reservationService, EntityServiceImpl entityService){
        this.entityService = entityService;
        this.reservationService = reservationService;
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
}
