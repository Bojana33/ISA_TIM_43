package isa2.demo.Service.ServiceImpl;

import isa2.demo.Model.Client;
import isa2.demo.Model.Reservation;
import isa2.demo.Repository.ReservationRepository;
import isa2.demo.Service.ReservationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    public final ReservationRepository reservationRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository){
        this.reservationRepository = reservationRepository;
    }

    @Override
    public List<Reservation> findByClient(Client client) {
        return this.reservationRepository.findAllByClient(client);
    }
}
