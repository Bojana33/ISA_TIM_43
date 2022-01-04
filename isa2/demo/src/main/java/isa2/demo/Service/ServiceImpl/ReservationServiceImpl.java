package isa2.demo.Service.ServiceImpl;

import isa2.demo.Model.Adventure;
import isa2.demo.Model.Owner;
import isa2.demo.Model.Reservation;
import isa2.demo.Repository.ReservationRepository;
import isa2.demo.Service.ReservationService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    private final AdventureServiceImpl adventureService;

    public ReservationServiceImpl(ReservationRepository reservationRepository, AdventureServiceImpl adventureService) {
        this.reservationRepository = reservationRepository;
        this.adventureService = adventureService;
    }

    @Override
    public Collection<Reservation> findAllReservationsForEntity(Integer entityId) {
        return reservationRepository.findAllByEntity_Id(entityId);
    }

    @Override
    public Collection<Reservation> findAllReservationsForInstructor(Owner owner) {
        Collection<Adventure> adventures = this.adventureService.findAdventuresByInstructor(owner);
        Collection<Reservation> reservations = new HashSet<>();
        for(Adventure adventure: adventures){
            Collection<Reservation> reservationsForAdventure = findAllReservationsForEntity(adventure.getId());
            reservations.addAll(reservationsForAdventure);
        }
        return reservations;
    }


}
