package isa2.demo.Repository;

import isa2.demo.Model.Client;
import isa2.demo.Model.Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
    boolean existsByIdAndSubscriptions(Integer client_id, Entity entity);
}
