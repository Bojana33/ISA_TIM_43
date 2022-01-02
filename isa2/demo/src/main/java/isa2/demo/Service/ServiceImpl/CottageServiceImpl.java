package isa2.demo.Service.ServiceImpl;

import isa2.demo.DTO.CottageDTO;
import isa2.demo.Model.Adventure;
import isa2.demo.Model.Cottage;
import isa2.demo.Model.Reservation;
import isa2.demo.Model.ReservationStatus;
import isa2.demo.Repository.CottageRepository;
import isa2.demo.Repository.PeriodRepository;
import isa2.demo.Repository.ReservationRepository;
import isa2.demo.Repository.UserRepository;
import isa2.demo.Service.CottageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CottageServiceImpl implements CottageService {

    //TO DO: Resolve this
    //final
    //CottageRepository cottageRepository;


    //public CottageServiceImpl(CottageRepository cottageRepository) {
      //  this.cottageRepository = cottageRepository;
    //}

    @Autowired
    CottageRepository cottageRepository;

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
        Cottage cottage = cottageRepository.findByIdAndReservationsIsNull(id);
        if(cottage != null){
            cottageRepository.deleteById(id);
        }else{
            throw new EntityNotFoundException("Cottage doesn't exist");
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
