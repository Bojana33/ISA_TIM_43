package isa2.demo.DTO;

import isa2.demo.Model.AvailabilityType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InstructorAvailabilityDTO {
    private Integer id;
    private PeriodDTO periodDTO;
    private Integer ownerId;
    private AvailabilityType availabilityType;
}
