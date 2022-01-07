package isa2.demo.DTO;

import isa2.demo.Model.Address;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private String firstName;
    private String surname;
    private String password;
    private String email;
    private String phoneNumber;
    private AddressDTO address;
    private Boolean firstLogin;
}
