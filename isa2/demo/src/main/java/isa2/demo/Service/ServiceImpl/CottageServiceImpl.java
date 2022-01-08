package isa2.demo.Service.ServiceImpl;

import isa2.demo.DTO.CottageDTO;
import isa2.demo.Model.*;
import isa2.demo.Repository.CottageRepository;
import isa2.demo.Repository.PeriodRepository;
import isa2.demo.Repository.ReservationRepository;
import isa2.demo.Repository.UserRepository;
import isa2.demo.Service.CottageService;
import isa2.demo.Service.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import org.springframework.stereotype.Service;

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
    public Cottage addNewCottage(Cottage cottage) {
        //TODO: uvezati cottageOwnera(Ulogovani user) sa vikendicom
        cottage.setSubscribedClients(Collections.EMPTY_LIST);
        return cottageRepository.save(cottage);

    }

    @Override
    public Cottage updateCottage(Cottage cottage) {
        Collection<Reservation> reservations = cottage.getReservations();
        reservations.removeIf(reservation -> (reservation.getReservationStatus() == ReservationStatus.FREE));
        if(reservations.isEmpty())
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
            Collection<Reservation> reservations = cottage.getReservations();
            reservations.removeIf(reservation -> (reservation.getReservationStatus() == ReservationStatus.FREE));
            if(reservations.isEmpty())
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
    public List<Cottage> findCottagesByName(String name) {
        return cottageRepository.findAllByNameContainingIgnoreCase(name);
    }

    @Override
    public Collection<Cottage> findFreeCottages(LocalDateTime startDate, LocalDateTime endDate){
        Collection<Cottage> cottages = cottageRepository.findAll();
        Collection<Cottage> freeCottages = new ArrayList<Cottage>();
        for (Cottage cottage : cottages) {
            if (!isPeriodInRentalTime(cottage, startDate, endDate))
                break;
            else
                freeCottages.add(cottage);
            Collection<Reservation> reservations = cottage.getReservations();
            for (Reservation reservation : reservations) {
                if (entityService.doTimeIntervalsIntersect(startDate, endDate, reservation.getReservedPeriod().getStartDate(), reservation.getReservedPeriod().getEndDate())) {
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
}
