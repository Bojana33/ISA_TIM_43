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
    private String cottageOwner_id;
    private String cottageName;
    private String description;
    private Set<String> photos;
    private Integer maxNumberOfGuests;
    private Double pricePerDay;
    private AddressDTO addressDTO;
    private Collection<RoomDTO> roomsDTO;
    private Collection<ReservationDTO> reservationsDTO;
}
