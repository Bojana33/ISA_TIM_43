package isa2.demo.Service.ServiceImpl;

import isa2.demo.Model.*;
import isa2.demo.Repository.ReservationRepository;
import isa2.demo.Service.ReservationService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    private final AdventureServiceImpl adventureService;

    private final CottageServiceImpl cottageService;

    private final BoatServiceImpl boatService;

    public ReservationServiceImpl(ReservationRepository reservationRepository, AdventureServiceImpl adventureService, CottageServiceImpl cottageService, BoatServiceImpl boatService) {
        this.reservationRepository = reservationRepository;
        this.adventureService = adventureService;
        this.cottageService = cottageService;
        this.boatService = boatService;
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
    public List<Reservation> findByClient(Client client) {
        return this.reservationRepository.findAllByClient(client);
    }


    @Override
    public Collection<Reservation> findAllReservationsForOwner(Owner owner) {
        Collection<Adventure> adventures = this.adventureService.findAdventuresByInstructor(owner);
        Collection<Cottage> cottages = this.cottageService.findCottagesByOwner(owner);
        Collection<Boat> boats = this.boatService.findBoatsByOwner(owner);
        Collection<Reservation> reservations = new HashSet<>();
        if (owner.getOwnerType() == OwnerType.INSTRUCTOR) {
            for (Adventure adventure : adventures) {
                Collection<Reservation> reservationsForAdventure = findAllReservationsForEntity(adventure.getId(), Optional.empty());
                reservations.addAll(reservationsForAdventure);
            }
        }
        if (owner.getOwnerType() == OwnerType.COTTAGEOWNER) {
            for (Cottage cottage : cottages) {
                Collection<Reservation> reservationsForCottage = findAllReservationsForEntity(cottage.getId(), Optional.empty());
                reservations.addAll(reservationsForCottage);
            }
        }
        if (owner.getOwnerType() == OwnerType.BOATOWNER) {
            for (Boat boat : boats) {
                Collection<Reservation> reservationsForBoat = findAllReservationsForEntity(boat.getId(), Optional.empty());
                reservations.addAll(reservationsForBoat);
            }
        }
        return reservations;
    }


}
