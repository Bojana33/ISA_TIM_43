package isa2.demo.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PeriodDTO {
    private Integer id;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Europe/Belgrade")
    private LocalDateTime startDate;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Europe/Belgrade")
    private LocalDateTime endDate;
}
