package isa2.demo.Service.ServiceImpl;

import isa2.demo.Model.Client;
import isa2.demo.Model.Entity;
import isa2.demo.Model.Reservation;
import isa2.demo.Model.User;
import isa2.demo.Repository.ClientRepository;
import isa2.demo.Service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientRepository clientRepository;

    @Override
    public Client findByUsername(String email) throws UsernameNotFoundException {
        Client u = clientRepository.findByEmail(email);
        return u;
    }

    @Override
    public Collection<Reservation> findAllReservations(String email) {
        Client client = this.findByUsername(email);
        Collection<Reservation> reservations = client.getReservation();
        return client.getReservation();
    }
}
