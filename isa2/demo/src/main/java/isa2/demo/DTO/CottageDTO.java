package isa2.demo.DTO;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import isa2.demo.Model.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.Set;

@Getter
@Setter
public class CottageDTO {
    private Integer id;
    private String cottageOwnerId;
    private String cottageName;
    private String description;
    private Set<String> photos;
    private Integer maxNumberOfGuests;
    private Double pricePerDay;
    private AddressDTO address;
    private Collection<RoomDTO> rooms;
    private Collection<ReservationDTO> reservations;
    private String entityPhoto;
    private Double averageGrade;
    private Collection<AdditionalServiceDTO> additionalServices;
}
