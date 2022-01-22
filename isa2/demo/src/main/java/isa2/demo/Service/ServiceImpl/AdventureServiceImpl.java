package isa2.demo.Service.ServiceImpl;

import isa2.demo.DTO.AdventureDTO;
import isa2.demo.DTO.BoatDTO;
import isa2.demo.DTO.FreeEntityDTO;
import isa2.demo.Exception.InvalidInputException;
import isa2.demo.Model.Adventure;
import isa2.demo.Model.Owner;
import isa2.demo.Model.Reservation;
import isa2.demo.Model.ReservationStatus;
import isa2.demo.Repository.AdventureRepository;
import isa2.demo.Service.AdventureService;
import isa2.demo.Service.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AdventureServiceImpl implements AdventureService {

    final
    AdventureRepository adventureRepository;

    final
    EntityService entityService;

    public AdventureServiceImpl(AdventureRepository adventureRepository, EntityService entityService) {
        this.adventureRepository = adventureRepository;
        this.entityService = entityService;
    }

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

    @Override
    public Collection<Adventure> findFreeAdventures(FreeEntityDTO request) throws InvalidInputException {
        if (request.getStartDate().isAfter(request.getEndDate()) || request.getStartDate().isEqual(request.getEndDate()))
            throw new InvalidInputException("Invalid start and end date");
        Collection<Adventure> adventures = adventureRepository.findAll();
        Collection<Adventure> freeAdventures = new ArrayList<Adventure>();
        if (request.getGrade() != null && request.getGrade() < 0)
            throw new InvalidInputException("Grade needs to be positive");
        if (request.getNumberOfGuests() != null && request.getNumberOfGuests() < 1)
            throw new InvalidInputException("Number of guests needs to be at least 1");
        for (Adventure adventure : adventures) {
            if ((request.getNumberOfGuests() != null && adventure.getMaxNumberOfGuests() < request.getNumberOfGuests()) || (adventure.getAverageGrade() == null && request.getGrade() != null)  || (request.getGrade() != null && adventure.getAverageGrade() < request.getGrade()))
                continue;
            if (request.getCountry() != null && !request.getCountry().equals(""))
                if (!request.getCountry().equals(adventure.getAddress().getCountry()))
                    continue;
            if (request.getCity() != null && !request.getCity().equals(""))
                if (!request.getCity().equals(adventure.getAddress().getCity()))
                    continue;
            if (!entityService.isPeriodInRentalTime(adventure, request.getStartDate(), request.getEndDate()))
                continue;
            else
                freeAdventures.add(adventure);
            Collection<Reservation> reservations = adventure.getReservations();
            for (Reservation reservation : reservations) {
                if (entityService.doTimeIntervalsIntersect(request.getStartDate(), request.getEndDate(), reservation.getReservedPeriod().getStartDate(), reservation.getReservedPeriod().getEndDate())) {
                    freeAdventures.remove(adventure);
                    break;
                }
            }
        }
        return freeAdventures;
    }

    Comparator<AdventureDTO> compareByPrice = new Comparator<AdventureDTO>() {
        @Override
        public int compare(AdventureDTO o1, AdventureDTO o2) {
            return o1.getPricePerDay().compareTo(o2.getPricePerDay());
        }
    };

    Comparator<AdventureDTO> compareByAverageGrade = new Comparator<AdventureDTO>() {
        @Override
        public int compare(AdventureDTO o1, AdventureDTO o2) {
            return o1.getAvgGrade().compareTo(o2.getAvgGrade());
        }
    };

    @Override
    public ArrayList<AdventureDTO> sortAdventures(Collection<AdventureDTO> adventures, String criterion, boolean asc){
        ArrayList<AdventureDTO> newList = new ArrayList<>(adventures);
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

    @Override
    public Owner getOwnerForAdventure(Integer adventureId){
        Adventure adventure = adventureRepository.findById(adventureId).get();
        return adventure.getOwner();
    }
}
