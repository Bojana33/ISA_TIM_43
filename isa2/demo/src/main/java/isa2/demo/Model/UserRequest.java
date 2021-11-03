package isa2.demo.Model;

import lombok.Data;

import javax.persistence.Column;

@Data
public class UserRequest {

    private Integer id;
    private String username;
    private String password;
    private String firstName;
    private String surname;
    private String email;
    private String phoneNumber;
}
