package isa2.demo.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import isa2.demo.Model.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.Collection;
@Getter
@Setter
public class ReservationDTO {

    private Integer id;
    @Positive
    private Float price;
//    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Europe/Belgrade")
//    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ssZ")
//    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalDateTime creationDate;
    private ReservationStatus reservationStatus;
    @Min(0)
    @Max(300)
    private Integer numberOfGuests;
    @Length(min = 5, max = 511)
    private String additionalNotes;
    private PeriodDTO reservedPeriod;
    private PeriodDTO salePeriod;
    private Collection<AdditionalServiceDTO> additionalServices;
    private Integer entityId;
    private Integer clientId;
    private UserComplaintDTO userComplaintDTO;
    @Positive
    private Double discount;
    @Positive
    private Double ownersIncome;
}
