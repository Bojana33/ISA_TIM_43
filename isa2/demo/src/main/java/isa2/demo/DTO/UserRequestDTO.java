package isa2.demo.DTO;

import isa2.demo.Model.Address;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
public class UserRequestDTO {

    private String firstName;
    private String surname;
    private String password;
    private String email;
    private String phoneNumber;
    private String verificationCode;
    private AddressDTO addressDTO;
}
