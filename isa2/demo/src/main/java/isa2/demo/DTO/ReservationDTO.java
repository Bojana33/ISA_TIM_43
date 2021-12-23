package isa2.demo.DTO;

import isa2.demo.Model.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
@Getter
@Setter
public class  ReservationDTO {

    private Integer id;

    private Float price;

    private Integer numberOfGuests;

    private String additionalNotes;

    private PeriodDTO reservedPeriod;

    private PeriodDTO salePeriod;

    private Collection<AdditionalServiceDTO> additionalServices;

}
