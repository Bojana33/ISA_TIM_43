package isa2.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import isa2.demo.Model.Client;
import isa2.demo.Model.Entity;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
    Client findByEmail(String email);
    Optional<Client> findById(Integer id);
    boolean existsByIdAndSubscriptions(Integer client_id, Entity entity);
}
