package isa2.demo.Service;

import isa2.demo.Model.InstructorAvailability;
import isa2.demo.Model.Owner;

import java.util.Collection;

public interface InstructorAvailabilityService {

    InstructorAvailability save(InstructorAvailability instructorAvailability);

    Collection<InstructorAvailability> findAllByOwner(Owner owner);
}
