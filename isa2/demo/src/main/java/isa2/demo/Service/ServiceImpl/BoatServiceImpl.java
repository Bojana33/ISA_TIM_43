package isa2.demo.Service.ServiceImpl;

import isa2.demo.DTO.BoatDTO;
import isa2.demo.DTO.CottageDTO;
import isa2.demo.Model.*;
import isa2.demo.DTO.FreeEntityDTO;
import isa2.demo.Exception.InvalidInputException;
import isa2.demo.Model.Adventure;
import isa2.demo.Model.Boat;
import isa2.demo.Model.Cottage;
import isa2.demo.Model.Reservation;
import isa2.demo.Repository.BoatRepository;
import isa2.demo.Repository.CottageRepository;
import isa2.demo.Service.BoatService;
import isa2.demo.Service.EntityService;
import isa2.demo.Service.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class BoatServiceImpl implements BoatService {
    final
    BoatRepository boatRepository;

    final
    EntityService entityService;

    public BoatServiceImpl(BoatRepository boatRepository, EntityService entityService) {
        this.boatRepository = boatRepository;
        this.entityService = entityService;
    }

    @Override
    public List<Boat> findAll() {
        return this.boatRepository.findAll();
    }

    @Override
    public Boat findOne(Integer id) {
        return this.boatRepository.findById(id).get();
    }

    @Override
    public List<Boat> findBoatsByOwner(Owner owner) {
        return this.boatRepository.findAllByOwner(owner);
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
    @Override
    public Boat deleteBoat(Integer boat_id) {
        Boat boat = boatRepository.findById(boat_id).orElse(null);
        if (boat != null){
            Collection<Reservation> reservations = new ArrayList<>(boat.getReservations());
            if(!(reservations.removeIf(reservation -> (reservation.getReservationStatus() == ReservationStatus.RESERVED))))
                boatRepository.deleteById(boat_id);
            else
                throw new UnsupportedOperationException("Entity with active reservations can't be deleted");
        }else {
            throw new EntityNotFoundException(boat_id.toString());
        }
        return boat;
    }

    @Override
    public Boat updateBoat(Boat boat) {
        Collection<Reservation> reservations = new ArrayList<>(boat.getReservations());
        if(!(reservations.removeIf(reservation -> (reservation.getReservationStatus() == ReservationStatus.RESERVED))))
            return boatRepository.save(boat);
        else
            throw new UnsupportedOperationException("Entity with active reservations can't be updated");
    }

    @Override
    public Collection<Boat> findFreeBoats(FreeEntityDTO request) throws InvalidInputException {
        if (request.getStartDate().isAfter(request.getEndDate()) || request.getStartDate().isEqual(request.getEndDate()))
            throw new InvalidInputException("Invalid start and end date");
        Collection<Boat> boats = boatRepository.findAll();
        Collection<Boat> freeBoats = new ArrayList<Boat>();
        if (request.getGrade() != null && request.getGrade() < 0)
            throw new InvalidInputException("Grade needs to be positive");
        if (request.getNumberOfGuests() != null && request.getNumberOfGuests() < 1)
            throw new InvalidInputException("Number of guests needs to be at least 1");
        for (Boat boat : boats) {
            if ((request.getNumberOfGuests() != null && boat.getMaxNumberOfGuests() < request.getNumberOfGuests()) || (boat.getAverageGrade() == null && request.getGrade() != null)  || (request.getGrade() != null && boat.getAverageGrade() < request.getGrade()))
                break;
            if (request.getCountry() != null && !request.getCountry().equals(""))
                if (!request.getCountry().equals(boat.getAddress().getCountry()))
                    break;
            if (request.getCity() != null && !request.getCity().equals(""))
                if (!request.getCity().equals(boat.getAddress().getCity()))
                    break;
            if (!entityService.isPeriodInRentalTime(boat, request.getStartDate(), request.getEndDate()))
                break;
            else
                freeBoats.add(boat);
            Collection<Reservation> reservations = boat.getReservations();
            for (Reservation reservation : reservations) {
                if (entityService.doTimeIntervalsIntersect(request.getStartDate(), request.getEndDate(), reservation.getReservedPeriod().getStartDate(), reservation.getReservedPeriod().getEndDate())) {
                    freeBoats.remove(boat);
                    break;
                }
            }
        }
        return freeBoats;
    }

    @Override
    public List<Boat> findBoatsByOwnerId(Integer id) {
        return boatRepository.findBoatsByOwner_id(id);
    }

    Comparator<BoatDTO> compareByPrice = new Comparator<BoatDTO>() {
        @Override
        public int compare(BoatDTO o1, BoatDTO o2) {
            return o1.getPricePerDay().compareTo(o2.getPricePerDay());
        }
    };

    Comparator<BoatDTO> compareByAverageGrade = new Comparator<BoatDTO>() {
        @Override
        public int compare(BoatDTO o1, BoatDTO o2) {
            return o1.getAvgGrade().compareTo(o2.getAvgGrade());
        }
    };

    @Override
    public ArrayList<BoatDTO> sortBoats(Collection<BoatDTO> boats, String criterion, boolean asc){
        ArrayList<BoatDTO> newList = new ArrayList<>(boats);
        if (criterion.equals("price") && asc)
            Collections.sort(newList, compareByPrice);
        else if (criterion.equals("grade") && asc)
            Collections.sort(newList, compareByAverageGrade);
        else if (criterion.equals("price") && !asc)
            Collections.sort(newList, compareByPrice.reversed());
        else
            Collections.sort(newList, compareByAverageGrade.reversed());
        return newList;
    }

}
