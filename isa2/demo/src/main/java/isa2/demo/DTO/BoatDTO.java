package isa2.demo.DTO;

import isa2.demo.Model.BoatType;
import isa2.demo.Model.NavigationEquipment;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.Collection;
import java.util.Set;

@Getter
@Setter
public class BoatDTO {
    private Integer id;
    private Integer boatOwnerId;
    @Length(min = 5, max = 63)
    private String name;
    @Length(min = 5, max = 511)
    private String description;
    @Min(0)
    @Max(300)
    private Integer maxNumberOfGuests;
    @Min(0)
    private Double pricePerDay;
    private AddressDTO address;
    @Min(0)
    private Double avgGrade;
    private Collection<ReservationDTO> reservations;
    private String entityPhoto;
    private Set<String> photos;
    @Min(0)
    @Max(1000)
    private Double length;
    @Min(0)
    @Max(1000)
    private Double enginePower;
    @Length(min = 5, max = 31)
    private String engineNumber;
    @Min(0)
    @Max(355)
    private Double maxSpeed;
    @Min(0)
    @Max(355)
    private Double capacity;
    @Min(0)
    @Max(100)
    private Double cancellationFee;
    @Length(min = 5, max = 511)
    private String houseRules;
    private String fishingEquipment;
    private BoatType type;
    private Set<NavigationEquipment> navigationEquipment;
}
