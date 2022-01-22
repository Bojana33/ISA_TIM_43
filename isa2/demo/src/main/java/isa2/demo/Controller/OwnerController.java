package isa2.demo.Controller;

import isa2.demo.Config.ModelMapperConfig;
import isa2.demo.DTO.AdventureDTO;
import isa2.demo.DTO.InstructorAvailabilityDTO;
import isa2.demo.DTO.PeriodDTO;
import isa2.demo.DTO.UserDTO;
import isa2.demo.Model.Adventure;
import isa2.demo.Model.InstructorAvailability;
import isa2.demo.Model.Owner;
import isa2.demo.Model.Period;
import isa2.demo.Service.ServiceImpl.InstructorAvailabilityServiceImpl;
import isa2.demo.Service.ServiceImpl.OwnerServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping(value = "/owner", produces = MediaType.APPLICATION_JSON_VALUE)
public class OwnerController {

    private final OwnerServiceImpl ownerService;

    private final ModelMapperConfig modelMapper;

    private final InstructorAvailabilityServiceImpl instructorAvailabilityService;

    public OwnerController(OwnerServiceImpl ownerService, ModelMapperConfig modelMapper, InstructorAvailabilityServiceImpl instructorAvailabilityService){
        this.ownerService = ownerService;
        this.modelMapper = modelMapper;
        this.instructorAvailabilityService = instructorAvailabilityService;
    }

    @PreAuthorize("hasRole('INSTRUCTOR')")
    @PostMapping(value = "/save_instructor_availability")
    public InstructorAvailability setInstructorAvailabilityPeriod(@RequestBody InstructorAvailabilityDTO instructorAvailabilityDTO){
        Owner owner = this.ownerService.findById(instructorAvailabilityDTO.getOwnerId());
        InstructorAvailability instructorAvailability = this.modelMapper.modelMapper().map(instructorAvailabilityDTO, InstructorAvailability.class);
        Period period = this.modelMapper.modelMapper().map(instructorAvailabilityDTO.getPeriodDTO(), Period.class);
        instructorAvailability.setOwner(owner);
        instructorAvailability.setPeriod(period);
        return this.instructorAvailabilityService.save(instructorAvailability);
    }

    @GetMapping("/get_instructor_availabilities/{instructorId}")
    public ResponseEntity<Collection<InstructorAvailabilityDTO>> getInstructorAvailabilities(@PathVariable Integer instructorId) {
        Owner owner = this.ownerService.findById(instructorId);
        Collection<InstructorAvailability> instructorAvailabilities = this.instructorAvailabilityService.findAllByOwner(owner);
        Collection<InstructorAvailabilityDTO> instructorAvailabilityDTOS = new ArrayList<>();
        PeriodDTO periodDTO;
        InstructorAvailabilityDTO instructorAvailabilityDTO;
        for (InstructorAvailability instructorAvailability : instructorAvailabilities) {
            periodDTO = this.modelMapper.modelMapper().map(instructorAvailability.getPeriod(), PeriodDTO.class);
            instructorAvailabilityDTO = modelMapper.modelMapper().map(instructorAvailability, InstructorAvailabilityDTO.class);
            instructorAvailabilityDTO.setPeriodDTO(periodDTO);
            instructorAvailabilityDTOS.add(instructorAvailabilityDTO);
        }
            return new ResponseEntity<>(instructorAvailabilityDTOS, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/getOwnerById/{ownerId}")
    public ResponseEntity<UserDTO> getOwnerById(@PathVariable Integer ownerId){
        Owner owner = this.ownerService.findById(ownerId);
        return new ResponseEntity<>(this.modelMapper.modelMapper().map(owner, UserDTO.class), HttpStatus.OK);
    }
}
