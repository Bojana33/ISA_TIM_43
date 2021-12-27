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
import isa2.demo.Repository.ClientRepository;
import isa2.demo.Repository.EntityRepository;
import isa2.demo.Service.ClientService;
import org.springframework.stereotype.Service;
import java.util.Collection;


@Service
public class ClientServiceImpl implements ClientService {
    private final EntityRepository entityRepository;
    private final ClientRepository clientRepository;


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
    public boolean subscribeToEntity(Integer client_id, Integer entity_id) {
       Entity entity = entityRepository.getById(entity_id);
       Client client = clientRepository.getById(client_id);
       if(!client.getSubscriptions().contains(entity)){
           Collection<Entity> entityCollection = client.getSubscriptions();
           entityCollection.add(entity);
           client.setSubscriptions(entityCollection);
           clientRepository.save(client);
           return true;
       }
       return false;
    }
}
