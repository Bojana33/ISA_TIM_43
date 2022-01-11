package isa2.demo.Service.ServiceImpl;

import isa2.demo.DTO.CottageDTO;
import isa2.demo.DTO.ReservationDTO;
import isa2.demo.Model.*;
import isa2.demo.Repository.CottageRepository;
import isa2.demo.Repository.PeriodRepository;
import isa2.demo.Repository.ReservationRepository;
import isa2.demo.Repository.UserRepository;
import isa2.demo.Service.CottageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    public CottageServiceImpl(CottageRepository cottageRepository) {
        this.cottageRepository = cottageRepository;
    }

    @Override
    public List<Cottage> findAll() {
        return this.cottageRepository.findAll();
    }

    @Override
    public Cottage addNewCottage(Cottage cottage) {
        cottage.setSubscribedClients(Collections.EMPTY_LIST);
        return cottageRepository.save(cottage);

    }

    @Override
    public Cottage updateCottage(Cottage cottage) {
        Collection<Reservation> reservations = new ArrayList<>(cottage.getReservations());
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
            Collection<Reservation> reservations = new ArrayList<>(cottage.getReservations());
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

}
