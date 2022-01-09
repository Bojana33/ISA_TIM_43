package isa2.demo.Service.ServiceImpl;

import isa2.demo.Model.*;
import isa2.demo.Repository.BoatRepository;
import isa2.demo.Service.BoatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class BoatServiceImpl implements BoatService {
    @Autowired
    BoatRepository boatRepository;

    @Override
    public List<Boat> findAll() {
        return this.boatRepository.findAll();
    }

    @Override
    public Boat findOne(Integer id) {
        return this.boatRepository.findById(id).get();
    }

    @Override
    public Boat addNewBoat(Boat boat) {
        boat.setSubscribedClients(Collections.EMPTY_LIST);
        Collection<Reservation> reservationsCollection = new HashSet<>();
        Collection<Reservation> boatReservations = boat.getReservations();
        if (!boatReservations.isEmpty()){
            for(Reservation reservation:boatReservations){
                reservation.setCreationDate(LocalDateTime.now());
                reservationsCollection.add(reservation);
            }
        }
        boat.setReservations(reservationsCollection);
        return boatRepository.save(boat);
    }

//    @Override
//    public Cottage deleteCottage(Integer id) throws EntityNotFoundException {
//        Cottage cottage = cottageRepository.findById(id).orElse(null);
//        if (cottage != null){
//            Collection<Reservation> reservations = cottage.getReservations();
//            reservations.removeIf(reservation -> (reservation.getReservationStatus() == ReservationStatus.FREE));
//            if(reservations.isEmpty())
//                cottageRepository.deleteById(id);
//            else
//                throw new UnsupportedOperationException("Entity with active reservations can't be deleted");
//        }else{
//            throw new EntityNotFoundException(id.toString());
//        }
//        return cottage;
//    }
    @Override
    public Boat deleteBoat(Integer boat_id) {
        Boat boat = boatRepository.findById(boat_id).orElse(null);
        if (boat != null){
            Collection<Reservation> reservations = new ArrayList<>(boat.getReservations());
            reservations.removeIf(reservation -> (reservation.getReservationStatus() == ReservationStatus.FREE));
            if(reservations.isEmpty())
                boatRepository.deleteById(boat_id);
            else
                throw new UnsupportedOperationException("Entity with active reservations can't be deleted");
        }else {
            throw new EntityNotFoundException(boat_id.toString());
        }
        return boat;
    }
}
