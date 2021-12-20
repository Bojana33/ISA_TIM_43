package isa2.demo.Service.ServiceImpl;

import isa2.demo.DTO.CottageDTO;
import isa2.demo.Model.Cottage;
import isa2.demo.Model.Reservation;
import isa2.demo.Repository.CottageRepository;
import isa2.demo.Repository.PeriodRepository;
import isa2.demo.Repository.ReservationRepository;
import isa2.demo.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class CottageServiceImpl implements isa2.demo.Service.CottageService {

    final
    CottageRepository cottageRepository;


    public CottageServiceImpl(CottageRepository cottageRepository) {
        this.cottageRepository = cottageRepository;
    }

    @Override
    public Cottage addNewCottage(Cottage cottage) {
        //TODO: uvezati cottageOwnera(Ulogovani user) sa vikendicom
        return cottageRepository.save(cottage);

    }

    @Override
    public Cottage updateCottage(Cottage cottage) {
        return cottageRepository.save(cottage);
    }

    @Override
    public List<Cottage> findAllCottages() {
        return cottageRepository.findAll();
    }

    @Override
    public void deleteCottage(Integer id) {
        Cottage cottage = cottageRepository.findByIdAndReservationsIsNull(id);
        if(cottage != null){
            cottageRepository.deleteById(id);
        }
    }
}
