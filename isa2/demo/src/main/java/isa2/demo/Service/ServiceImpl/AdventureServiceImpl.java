package isa2.demo.Service.ServiceImpl;

import isa2.demo.DTO.FreeEntityDTO;
import isa2.demo.Exception.InvalidInputException;
import isa2.demo.Model.*;
import isa2.demo.Repository.AdventureRepository;
import isa2.demo.Service.AdventureService;
import isa2.demo.Service.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

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
        Collection<InstructorAvailability> instructorAvailabilities = adventure.getOwner().getAvailabilityPeriods();
        Collection<RentalTime> rentalTimes = new ArrayList<>();
        for(InstructorAvailability instructorAvailability: instructorAvailabilities){
            if(instructorAvailability.getAvailabilityType() == AvailabilityType.AVAILABLE){
                RentalTime rentalTime = new RentalTime();
                rentalTime.setStart_date(instructorAvailability.getPeriod().getStartDate());
                rentalTime.setEnd_date(instructorAvailability.getPeriod().getEndDate());
                rentalTimes.add(rentalTime);
            }
        }
        adventure.setRentalTimes(rentalTimes);
        return this.adventureRepository.save(adventure);
    }

    @Override
    public Adventure update(Adventure adventure) {
        Collection<Reservation> reservations = new ArrayList<>(adventure.getReservations());
        if(!(reservations.removeIf(reservation -> (reservation.getReservationStatus() == ReservationStatus.RESERVED))))
            return adventureRepository.save(adventure);
        else
            throw new UnsupportedOperationException("Entity with active reservations can't be updated");


    }

    @Override
    public void delete(Integer id) {
        Adventure adventure = this.adventureRepository.findById(id).orElse(null);
        Collection<Reservation> reservations = new ArrayList<>(adventure.getReservations());
        if(!(reservations.removeIf(reservation -> (reservation.getReservationStatus() == ReservationStatus.RESERVED))))
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
                break;
            if (request.getCountry() != null && !request.getCountry().equals(""))
                if (!request.getCountry().equals(adventure.getAddress().getCountry()))
                    break;
            if (request.getCity() != null && !request.getCity().equals(""))
                if (!request.getCity().equals(adventure.getAddress().getCity()))
                    break;
            if (!entityService.isPeriodInRentalTime(adventure, request.getStartDate(), request.getEndDate()))
                break;
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
}
