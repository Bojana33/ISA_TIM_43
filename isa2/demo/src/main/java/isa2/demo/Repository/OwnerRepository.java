package isa2.demo.Repository;

import isa2.demo.Model.Adventure;
import isa2.demo.Model.Boat;
import isa2.demo.Model.Cottage;
import isa2.demo.Model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerRepository extends JpaRepository<Owner,Integer> {

    Owner findByAdventures(Adventure adventure);

    Owner findByCottages(Cottage cottage);

    Owner findByBoat(Boat boat);
}
