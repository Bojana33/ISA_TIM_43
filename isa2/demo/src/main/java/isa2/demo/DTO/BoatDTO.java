package isa2.demo.DTO;

import isa2.demo.Model.BoatType;
import isa2.demo.Model.NavigationEquipment;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.Set;

@Getter
@Setter
public class BoatDTO {
    private Integer id;
    private Integer boatOwnerId;
    private String name;
    private String description;
    private Integer maxNumberOfGuests;
    private Double pricePerDay;
    private AddressDTO address;
    private Double avgGrade;
    //private Collection<RoomDTO> rooms;
    private Collection<ReservationDTO> reservations;
    private String entityPhoto;
    private Set<String> photos;
    private Double length;
    private Double enginePower;
    private String engineNumber;
    private Double maxSpeed;
    private Double capacity;
    private Double cancellationFee;
    private String houseRules;
    private String fishingEquipment;
    private BoatType type;
    private Set<NavigationEquipment> navigationEquipment;
}
