package isa2.demo.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
public class PeriodDTO {
    private Integer id;
//    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Europe/Belgrade")
   @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalDateTime startDate;
//    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Europe/Belgrade")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalDateTime endDate;
}
