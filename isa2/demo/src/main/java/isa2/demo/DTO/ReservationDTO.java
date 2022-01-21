package isa2.demo.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import isa2.demo.Model.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Collection;
@Getter
@Setter
public class ReservationDTO {

    private Integer id;
    private Float price;
//    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Europe/Belgrade")
//    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ssZ")
//    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalDateTime creationDate;
    private ReservationStatus reservationStatus;
    private Integer numberOfGuests;
    private String additionalNotes;
    private PeriodDTO reservedPeriod;
    private PeriodDTO salePeriod;
    private Collection<AdditionalServiceDTO> additionalServices;
    private Integer entityId;
    private Integer clientId;
    private UserComplaintDTO userComplaintDTO;
    private Double discount;
    private Double ownersIncome;
}
