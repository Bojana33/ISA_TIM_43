package isa2.demo.DTO;

import isa2.demo.Model.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class CottageDTO {
    private String cottageOwnerName;
    private String cottageName;
    private String description;
    private Set<String> photos;
    private Double averageGrade;
    private Integer maxNumberOfGuests;
    private Double pricePerDay;
    private Address address;

}
