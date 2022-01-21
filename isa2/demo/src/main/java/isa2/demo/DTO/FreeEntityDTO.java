package isa2.demo.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
public class FreeEntityDTO {
    private Integer numberOfGuests;
    private String type;
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalDateTime startDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalDateTime endDate;
    //private AddressDTO address;
    private String country;
    private String city;
    private Double grade;
}
