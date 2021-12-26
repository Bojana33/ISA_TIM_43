package isa2.demo.Service.ServiceImpl;

import isa2.demo.Model.Client;
import isa2.demo.Model.Entity;
import isa2.demo.Repository.ClientRepository;
import isa2.demo.Repository.EntityRepository;
import isa2.demo.Service.ClientService;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final EntityRepository entityRepository;

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
