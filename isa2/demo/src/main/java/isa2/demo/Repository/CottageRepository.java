package isa2.demo.Repository;

import isa2.demo.Model.Cottage;
import isa2.demo.Model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CottageRepository extends JpaRepository<Cottage, Integer> {
    Cottage findByIdAndReservationsIsNull(Integer id);
    List<Cottage> findAllByOwner_idAndNameContainingIgnoreCase(Integer ownerId, String name);
    List<Cottage> findAllByOwner(Owner owner);
    List<Cottage> findCottageByOwner_id(Integer id);
}
