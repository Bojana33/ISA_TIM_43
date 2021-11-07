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

@Service
public class CottageServiceImpl implements isa2.demo.Service.CottageService {

    final
    CottageRepository cottageRepository;

    final ReservationRepository reservationRepository;
    public CottageServiceImpl(CottageRepository cottageRepository,  ReservationRepository reservationRepository) {
        this.cottageRepository = cottageRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public Cottage addNewCottage(Cottage cottage) {
        //TODO: uvezati cottageOwnera(Ulogovani user) sa vikendicom i ROOMS
        return cottageRepository.save(cottage);

    }
}
