package isa2.demo.Model;

import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class Address {

    private java.lang.String city;

    private java.lang.String country;

    private java.lang.String street;

    private java.lang.String number;


    public java.util.Collection<User> user;

    public java.util.Collection<RegistrationRequest> registrationRequest;

    public java.util.Collection<Entity> entity;


}