package isa2.demo.Service.ServiceImpl;

import isa2.demo.Model.*;
import isa2.demo.Repository.ClientRepository;
import isa2.demo.Service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import isa2.demo.Repository.ClientRepository;
import isa2.demo.Repository.EntityRepository;
import isa2.demo.Service.ClientService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;


@Service
public class ClientServiceImpl implements ClientService {
    private final EntityRepository entityRepository;
    private final ClientRepository clientRepository;

    @Override
    public Client findById(Integer id) {
        return clientRepository.findById(id).orElse(null);
    }

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

    public ClientServiceImpl(ClientRepository clientRepository, EntityRepository entityRepository) {
        this.clientRepository = clientRepository;
        this.entityRepository = entityRepository;
    }

    @Override
    public boolean subscribeToEntity(String username, Integer entity_id) {
       Entity entity = entityRepository.getById(entity_id);
       Client client = findByUsername(username);
       if(!client.getSubscriptions().contains(entity)){
           Collection<Entity> entityCollection = client.getSubscriptions();
           entityCollection.add(entity);
           client.setSubscriptions(entityCollection);
           clientRepository.save(client);
           return true;
       }
       return false;
    }

    @Override
    public void unsubscribe(String username, Integer entity_id) throws Exception{
        Entity entity = entityRepository.getById(entity_id);
        Client client = findByUsername(username);
        Collection<Entity> entities = client.getSubscriptions();
        if(!entities.contains(entity))
            throw new Exception("Client is not subscribed");
        entities.remove(entity);
        client.setSubscriptions(entities);
        clientRepository.save(client);
    }

    @Override
    public boolean isSubscribed(String username, Integer entity_id){
        Entity entity = entityRepository.getById(entity_id);
        Client client = findByUsername(username);
        if(client.getSubscriptions().contains(entity))
            return true;
        return false;
    }

    @Override
    public Collection<Entity> getAllSubscriptions(String username){
        Client client = findByUsername(username);
        return client.getSubscriptions();
    }

    @Override
    public Collection<Reservation> getAllFutureReservations(String username){
        Collection<Reservation> reservations = findByUsername(username).getReservation();
        Collection<Reservation> futureReservations = new ArrayList<>();
        for(Reservation reservation : reservations)
            if (reservation.getReservedPeriod().getStartDate().isAfter(LocalDateTime.now()) && reservation.getReservationStatus() == ReservationStatus.RESERVED)
                futureReservations.add(reservation);
        return futureReservations;
    }

    @Override
    public void refreshPenaltyPoints(){
        Collection<Client> clients = clientRepository.findAllByDeleted(false);
        for (Client client: clients){
            client.setPenalty(0);
            clientRepository.save(client);
        }
    }

    @Override
    public Collection<Reservation> reservationsHistory(String username, String criterion){
        Collection<Reservation> reservations = findByUsername(username).getReservation();
        Collection<Reservation> reservationsHistory = new ArrayList<>();
        for (Reservation reservation : reservations){
            if(criterion.equals("cottage") && reservation.getEntity().getClass() == Cottage.class && reservation.getReservedPeriod().getEndDate().isBefore(LocalDateTime.now()))
                reservationsHistory.add(reservation);
            else if(criterion.equals("boat") && reservation.getEntity().getClass() == Boat.class && reservation.getReservedPeriod().getEndDate().isBefore(LocalDateTime.now()))
                reservationsHistory.add(reservation);
            else if(criterion.equals("adventure") && reservation.getEntity().getClass() == Adventure.class && reservation.getReservedPeriod().getEndDate().isBefore(LocalDateTime.now()))
                reservationsHistory.add(reservation);
            else if (criterion.equals("all") && reservation.getReservedPeriod().getEndDate().isBefore(LocalDateTime.now()))
                reservationsHistory.add(reservation);
        }
        return reservationsHistory;
    }
}
