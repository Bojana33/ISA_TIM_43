package isa2.demo.Service;

import isa2.demo.Model.Client;
import isa2.demo.Model.Reservation;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;

public interface ClientService {
    Client findByUsername(String email) throws UsernameNotFoundException;
    Client findById(Integer id);
    Collection<Reservation> findAllReservations(String email);
    boolean subscribeToEntity(String username, Integer entity_id);
}
