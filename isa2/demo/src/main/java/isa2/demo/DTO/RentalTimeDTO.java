package isa2.demo.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
public class RentalTimeDTO {
    private Integer id;
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalDateTime start_date;
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalDateTime end_date;
    private Integer entity_id;
}
