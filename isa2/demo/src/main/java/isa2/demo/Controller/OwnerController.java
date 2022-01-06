package isa2.demo.Controller;

import isa2.demo.DTO.PeriodDTO;
import isa2.demo.Model.Owner;
import isa2.demo.Service.ServiceImpl.OwnerServiceImpl;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.awt.*;

@RestController
@RequestMapping(value = "/owner", produces = MediaType.APPLICATION_JSON_VALUE)
public class OwnerController {

    private final OwnerServiceImpl ownerService;

    public OwnerController(OwnerServiceImpl ownerService){
        this.ownerService = ownerService;
    }

    @PostMapping(value = "/set_instructor_availability_period/{instructorId}/{status}")
    public void setInstructorAvailabilityPeriod(@RequestBody PeriodDTO period, @PathVariable Integer status, @PathVariable Integer instructorId){
        Owner owner = this.ownerService.findById(instructorId);
        this.ownerService.setAvailability(owner, period, status);
    }

}
