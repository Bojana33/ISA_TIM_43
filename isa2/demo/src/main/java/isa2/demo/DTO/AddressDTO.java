package isa2.demo.DTO;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;

@Getter
@Setter
public class AddressDTO {
    private java.lang.Integer id;
    @Length(min = 5, max = 63)
    private java.lang.String city;
    @Length(min = 5, max = 63)
    private java.lang.String country;
    @Length(min = 5, max = 63)
    private java.lang.String street;
    @Length(min = 5, max = 63)
    private java.lang.String houseNumber;
}
