package isa2.demo.DTO;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
public class AddressDTO {
    private java.lang.Integer id;
    private java.lang.String city;
    private java.lang.String country;
    private java.lang.String street;
    private java.lang.String houseNumber;
}
