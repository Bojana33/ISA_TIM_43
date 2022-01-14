package isa2.demo.Service.ServiceImpl;

import isa2.demo.Model.Adventure;
import isa2.demo.Model.Owner;
import isa2.demo.Model.Reservation;
import isa2.demo.Model.ReservationStatus;
import isa2.demo.Repository.AdventureRepository;
import isa2.demo.Service.AdventureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class AdventureServiceImpl implements AdventureService {

    @Autowired
    private AdventureRepository adventureRepository;

    @Override
    public List<Adventure> findAll() {
        return this.adventureRepository.findAll();
    }

    @Override
    public Optional<Adventure> findOne(Integer id) {
        return this.adventureRepository.findById(id);
    }

    @Override
    public Adventure save(Adventure adventure) {
        return this.adventureRepository.save(adventure);
    }

    @Override
    public Adventure update(Adventure adventure) {
        Collection<Reservation> reservations = new ArrayList<>(adventure.getReservations());
        reservations.removeIf(reservation -> (reservation.getReservationStatus() == ReservationStatus.FREE));
        if(reservations.isEmpty())
            return this.adventureRepository.save(adventure);
        else
            throw new UnsupportedOperationException("Entity with active reservations can't be deleted");


    }

    @Override
    public void delete(Integer id) {
        Adventure adventure = this.adventureRepository.findById(id).orElse(null);
        Collection<Reservation> reservations = new ArrayList<>(adventure.getReservations());
        reservations.removeIf(reservation -> (reservation.getReservationStatus() == ReservationStatus.FREE));
        if(reservations.isEmpty())
            this.adventureRepository.deleteById(id);
        else
            throw new UnsupportedOperationException("Entity with active reservations can't be deleted");
    }

    @Override
    public List<Adventure> findAdventuresByInstructor(Owner owner) {
        return this.adventureRepository.findAllByOwner(owner);
    }
}
