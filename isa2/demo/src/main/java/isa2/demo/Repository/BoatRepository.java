package isa2.demo.Repository;

import isa2.demo.Model.Boat;
import isa2.demo.Model.Cottage;
import isa2.demo.Model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface BoatRepository extends JpaRepository<Boat, Integer> {
    List<Boat> findAllByOwner(Owner owner);
    List<Boat> findBoatsByOwner_id(Integer id);
}
