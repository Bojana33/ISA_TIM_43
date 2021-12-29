package isa2.demo.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import isa2.demo.Model.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Collection;
@Getter
@Setter
public class ReservationDTO {

    private Integer id;
    private Float price;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Europe/Belgrade")
    private LocalDateTime creationDate;
    private ReservationStatus reservationStatus;
    private Integer numberOfGuests;
    private String additionalNotes;
    private PeriodDTO reservedPeriod;
    private PeriodDTO salePeriod;
    private Collection<AdditionalServiceDTO> additionalServices;
    private Integer entityId;

}
