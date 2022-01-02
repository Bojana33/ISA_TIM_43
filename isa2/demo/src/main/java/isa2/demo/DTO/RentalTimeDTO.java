package isa2.demo.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
public class RentalTimeDTO {
    private Integer id;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Europe/Belgrade")
    private LocalDateTime start_date;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Europe/Belgrade")
    private LocalDateTime end_date;
    private Integer entity_id;
}
