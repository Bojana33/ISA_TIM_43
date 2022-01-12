package isa2.demo.Repository;

import isa2.demo.Model.Entity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntityRepository extends JpaRepository<Entity,Integer> {

}
