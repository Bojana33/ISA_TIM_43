package isa2.demo.DTO;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Positive;

@Getter
@Setter
public class RoomDTO {
    private Integer id;
    @Positive
    private Integer numberOfBeds;
}
