package isa2.demo.DTO;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
public class RentalTimeDTO {
    private Integer id;
    private LocalDateTime start_date;
    private LocalDateTime end_date;
    private Integer entity_id;
}
