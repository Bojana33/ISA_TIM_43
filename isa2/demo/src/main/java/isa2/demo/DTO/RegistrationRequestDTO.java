package isa2.demo.DTO;

import isa2.demo.Model.Address;
import isa2.demo.Model.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
public class RegistrationRequestDTO {
    private String firstName;
    private String surname;
    private String password;
    private String email;
    private String phoneNumber;
    private String registrationExplanation;
    private UserType userType;
    private AddressDTO addressDTO;
}
