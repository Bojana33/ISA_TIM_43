package isa2.demo.Service.ServiceImpl;

import isa2.demo.Model.Adventure;
import isa2.demo.Model.Owner;
import isa2.demo.Model.Period;
import isa2.demo.Model.Reservation;
import isa2.demo.Repository.ReservationRepository;
import isa2.demo.Service.ReservationService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    private final AdventureServiceImpl adventureService;

    public ReservationServiceImpl(ReservationRepository reservationRepository, AdventureServiceImpl adventureService) {
        this.reservationRepository = reservationRepository;
        this.adventureService = adventureService;
    }

    @Override
    public Collection<Reservation> findAllReservationsForEntity(Integer entityId, Optional<Period> period) {
        Collection<Reservation> reservations = new ArrayList<>();
        if(period.isPresent()){
            reservations = reservationRepository.findAllByEntity_IdAndReservedPeriod_StartDateAfterAndReservedPeriod_EndDateBefore(entityId,period.get().getStartDate(),period.get().getEndDate());
        }else{
            reservations = reservationRepository.findAllByEntity_Id(entityId);
        }
        return reservations;
    }

    @Override
    public Collection<Reservation> findAllReservationsForInstructor(Owner owner) {
        Collection<Adventure> adventures = this.adventureService.findAdventuresByInstructor(owner);
        Collection<Reservation> reservations = new HashSet<>();
        for(Adventure adventure: adventures){
            Collection<Reservation> reservationsForAdventure = findAllReservationsForEntity(adventure.getId(), Optional.empty());
            reservations.addAll(reservationsForAdventure);
        }
        return reservations;
    }


}
