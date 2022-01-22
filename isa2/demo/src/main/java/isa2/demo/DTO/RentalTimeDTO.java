package isa2.demo.DTO;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
public class RentalTimeDTO {
    private Integer id;
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalDateTime startDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalDateTime endDate;
    private Integer entityId;
}
