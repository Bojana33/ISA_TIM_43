package isa2.demo.DTO;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import isa2.demo.Model.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.util.Collection;
import java.util.Set;

@Getter
@Setter
public class CottageDTO {
    private Integer id;
    private String cottageOwnerId;
    @Length(min = 5, max = 63)
    private String cottageName;
    @Length(min = 5, max = 511)
    private String description;
    @Min(0)
    private Double avgGrade;
    private Set<String> photos;
    @Min(0)
    @Max(300)
    private Integer maxNumberOfGuests;
    @Positive
    private Double pricePerDay;
    private AddressDTO address;
    private Collection<RoomDTO> rooms;
    private Collection<ReservationDTO> reservations;
    private String entityPhoto;
    @Min(0)
    private Double averageGrade;
    private Collection<AdditionalServiceDTO> additionalServices;
}
