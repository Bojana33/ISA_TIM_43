package isa2.demo.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.Set;

@Getter
@Setter
public class AdventureDTO {
    private Integer id;
    private Integer adventureOwnerId;
    private String name;
    private AddressDTO addressDTO;
    private String description;
    private String instructorBio;
    private Double avgGrade;
    private Set<String> photos;
    private Integer maxNumberOfGuests;
    private String houseRules;
    private String defaultFishingEquipment;
    private Double pricePerDay;
    private Integer cancellationFee;
    private String entityPhoto;
    private Collection<ReservationDTO> reservations;
    private Collection<AdditionalServiceDTO> additionalServices;
}
