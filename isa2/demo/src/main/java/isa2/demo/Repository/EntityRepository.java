package isa2.demo.Repository;

import isa2.demo.Model.Entity;
import isa2.demo.Model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface EntityRepository extends JpaRepository<Entity,Integer> {

    Entity findByReservations(Reservation reservation);
}
