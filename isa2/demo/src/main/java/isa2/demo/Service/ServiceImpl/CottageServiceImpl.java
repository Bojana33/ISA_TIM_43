package isa2.demo.Service.ServiceImpl;

import isa2.demo.DTO.CottageDTO;
import isa2.demo.DTO.FreeEntityDTO;
import isa2.demo.Exception.InvalidInputException;
import isa2.demo.Model.*;
import isa2.demo.Repository.CottageRepository;
import isa2.demo.Service.CottageService;
import isa2.demo.Service.EntityService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.List;

@Service
public class CottageServiceImpl implements CottageService {

    final
    CottageRepository cottageRepository;

    final
    EntityService entityService;

    public CottageServiceImpl(CottageRepository cottageRepository, EntityService entityService) {
        this.cottageRepository = cottageRepository;
        this.entityService = entityService;
    }

    @Override
    public List<Cottage> findAll() {
        return this.cottageRepository.findAll();
    }

    @Override
    public List<Cottage> findCottagesByOwner(Owner owner) {
        return this.cottageRepository.findAllByOwner(owner);
    }

    @Override
    public Cottage addNewCottage(Cottage cottage) {
        cottage.setSubscribedClients(Collections.EMPTY_LIST);
        return cottageRepository.save(cottage);

    }

    @Override
    public Cottage updateCottage(Cottage cottage) {
        Collection<Reservation> reservations = new ArrayList<>(cottage.getReservations());
        if(!(reservations.removeIf(reservation -> (reservation.getReservationStatus() == ReservationStatus.RESERVED))))
            return cottageRepository.save(cottage);
        else
            throw new UnsupportedOperationException("Entity with active reservations can't be updated");

    }

    @Override
    public List<Cottage> findAllCottages() {
        return cottageRepository.findAll();
    }

    @Override
    public Cottage deleteCottage(Integer id) throws EntityNotFoundException {
        Cottage cottage = cottageRepository.findById(id).orElse(null);
        if (cottage != null){
            Collection<Reservation> reservations = new ArrayList<>(cottage.getReservations());
            if(!(reservations.removeIf(reservation -> (reservation.getReservationStatus() == ReservationStatus.RESERVED))))
                cottageRepository.deleteById(id);
            else
                throw new UnsupportedOperationException("Entity with active reservations can't be deleted");
        }else{
            throw new EntityNotFoundException(id.toString());
        }
        return cottage;
    }

    @Override
    public Optional<Cottage> findById(Integer id) {
        return cottageRepository.findById(id);
    }

    @Override
    public List<Cottage> findOwnerCottagesByName(String name, Integer id) {
        return cottageRepository.findAllByOwner_idAndNameContainingIgnoreCase(id, name);
    }

    @Override
    public Collection<Cottage> findFreeCottages(FreeEntityDTO request) throws InvalidInputException {
        if (request.getStartDate().isAfter(request.getEndDate()) || request.getStartDate().isEqual(request.getEndDate()))
            throw new InvalidInputException("Invalid start and end date");
        Collection<Cottage> cottages = cottageRepository.findAll();
        Collection<Cottage> freeCottages = new ArrayList<Cottage>();
        if (request.getGrade() != null && request.getGrade() < 0)
            throw new InvalidInputException("Grade needs to be positive");
        if (request.getNumberOfGuests() != null && request.getNumberOfGuests() < 1)
            throw new InvalidInputException("Number of guests needs to be at least 1");
        for (Cottage cottage : cottages) {
            if (
                    (request.getNumberOfGuests() != null && cottage.getMaxNumberOfGuests() < request.getNumberOfGuests())
                    || (cottage.getAverageGrade() == null && request.getGrade() != null)
                    || (request.getGrade() != null && cottage.getAverageGrade() < request.getGrade()))

                continue;
            if (request.getCountry() != null && !request.getCountry().equals(""))
                if (!checkLocation(request.getCountry(), cottage.getAddress().getCountry()))
                    continue;
            if (request.getCity() != null && !request.getCity().equals(""))
                if (!checkLocation(request.getCity(), cottage.getAddress().getCity()))
                    continue;
            if (!isPeriodInRentalTime(cottage, request.getStartDate(), request.getEndDate()))
                continue;
            else
                freeCottages.add(cottage);
            Collection<Reservation> reservations = cottage.getReservations();
            for (Reservation reservation : reservations) {
                if (entityService.doTimeIntervalsIntersect(request.getStartDate(), request.getEndDate(), reservation.getReservedPeriod().getStartDate(), reservation.getReservedPeriod().getEndDate())) {
                    freeCottages.remove(cottage);
                    break;
                }
            }
        }
        return freeCottages;
    }

    private boolean isPeriodInRentalTime(Entity entity, LocalDateTime startDate, LocalDateTime endDate) {
        Collection<RentalTime> rentalTimes = entity.getRentalTimes();
        for (RentalTime rentalTime : rentalTimes) {
            if(rentalTime.getStart_date().isBefore(startDate) && rentalTime.getEnd_date().isAfter(endDate))
                return true;
        }
        return false;
    }

    private boolean checkLocation(String address1, String address2){
        if(!address1.equals(address2))
            return false;
        return true;
    }

    Comparator<CottageDTO> compareByPrice = new Comparator<CottageDTO>() {
        @Override
        public int compare(CottageDTO o1, CottageDTO o2) {
            return o1.getPricePerDay().compareTo(o2.getPricePerDay());
        }
    };

    Comparator<CottageDTO> compareByAverageGrade = new Comparator<CottageDTO>() {
        @Override
        public int compare(CottageDTO o1, CottageDTO o2) {
            return o1.getAverageGrade().compareTo(o2.getAverageGrade());
        }
    };

    @Override
    public ArrayList<CottageDTO> sortCottages(Collection<CottageDTO> cottages, String criterion, boolean asc){
        ArrayList<CottageDTO> newList = new ArrayList<>(cottages);
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
    public List<Cottage> findCottagesByOwnerId(Integer id) {
        return cottageRepository.findCottageByOwner_id(id);
    }
}
