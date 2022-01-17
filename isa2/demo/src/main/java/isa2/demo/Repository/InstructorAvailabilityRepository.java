package isa2.demo.Repository;

import isa2.demo.Model.InstructorAvailability;
import isa2.demo.Model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface InstructorAvailabilityRepository extends JpaRepository<InstructorAvailability,Integer> {

    Collection<InstructorAvailability> findAllByOwner(Owner owner);
}
