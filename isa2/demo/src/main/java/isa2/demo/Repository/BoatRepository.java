package isa2.demo.Repository;

import isa2.demo.Model.Boat;
import isa2.demo.Model.Cottage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoatRepository extends JpaRepository<Boat, Integer> {

}
