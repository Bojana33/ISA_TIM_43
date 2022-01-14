package isa2.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import isa2.demo.Model.Client;
import isa2.demo.Model.Entity;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
    Client findByEmail(String email);
    boolean existsByIdAndSubscriptions(Integer client_id, Entity entity);
}
