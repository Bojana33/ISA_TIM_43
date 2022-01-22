package isa2.demo.Service.ServiceImpl;

import isa2.demo.Model.*;
import isa2.demo.Repository.InstructorAvailabilityRepository;
import isa2.demo.Service.InstructorAvailabilityService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class InstructorAvailabilityServiceImpl implements InstructorAvailabilityService {

    private final InstructorAvailabilityRepository instructorAvailabilityRepository;
    private final AdventureServiceImpl adventureService;

    public InstructorAvailabilityServiceImpl(InstructorAvailabilityRepository instructorAvailabilityRepository, AdventureServiceImpl adventureService){
        this.instructorAvailabilityRepository = instructorAvailabilityRepository;
        this.adventureService = adventureService;
    }

    @Override
    public InstructorAvailability save(InstructorAvailability instructorAvailability) {
        Collection<RentalTime> rentalTimes = new ArrayList<>();
        Collection<Adventure> adventures = this.adventureService.findAdventuresByInstructor(instructorAvailability.getOwner());
        if(instructorAvailability.getAvailabilityType() == AvailabilityType.AVAILABLE){
            RentalTime rentalTime = new RentalTime();
            rentalTime.setStart_date(instructorAvailability.getPeriod().getStartDate());
            rentalTime.setEnd_date(instructorAvailability.getPeriod().getEndDate());
            for(Adventure adventure: adventures){
                rentalTimes.add(rentalTime);
                adventure.setRentalTimes(rentalTimes);
                this.adventureService.update(adventure);
            }
        }

        return this.instructorAvailabilityRepository.save(instructorAvailability);
    }

    @Override
    public Collection<InstructorAvailability> findAllByOwner(Owner owner) {
        return this.instructorAvailabilityRepository.findAllByOwner(owner);
    }
}
