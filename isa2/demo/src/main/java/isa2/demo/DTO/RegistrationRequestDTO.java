package isa2.demo.DTO;

import isa2.demo.Model.OwnerType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationRequestDTO {
    private Integer id;
    private String firstName;
    private String surname;
    private String password;
    private String email;
    private String phoneNumber;
    private String registrationExplanation;
    private OwnerType ownerType;
    private AddressDTO addressDTO;
    private String rejectionReason;
}
