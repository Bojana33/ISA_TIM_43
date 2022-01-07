package isa2.demo.Service.ServiceImpl;

import isa2.demo.Model.InstructorAvailability;
import isa2.demo.Model.Owner;
import isa2.demo.Repository.InstructorAvailabilityRepository;
import isa2.demo.Service.InstructorAvailabilityService;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class InstructorAvailabilityServiceImpl implements InstructorAvailabilityService {

    private final InstructorAvailabilityRepository instructorAvailabilityRepository;

    public InstructorAvailabilityServiceImpl(InstructorAvailabilityRepository instructorAvailabilityRepository){
        this.instructorAvailabilityRepository = instructorAvailabilityRepository;
    }

    @Override
    public InstructorAvailability save(InstructorAvailability instructorAvailability) {
        return this.instructorAvailabilityRepository.save(instructorAvailability);
    }

    @Override
    public Collection<InstructorAvailability> findAllByOwner(Owner owner) {
        return this.instructorAvailabilityRepository.findAllByOwner(owner);
    }
}
