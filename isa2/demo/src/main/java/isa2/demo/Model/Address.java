package isa2.demo.Model;

import lombok.Data;

import javax.persistence.*;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Collections;

@Entity
@Data
@Table(name = "addresses")
public class Address implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id", unique = true, nullable = false)
    private Integer id;

    @Column
    private java.lang.String city;
    @Column
    private java.lang.String country;
    @Column
    private java.lang.String street;
    @Column
    private java.lang.String number;


    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id" , referencedColumnName = "user_id")
    public java.util.Set<User> user;

    @OneToMany
    public java.util.Collection<RegistrationRequest> registrationRequest;

    @OneToOne
    public isa2.demo.Model.Entity entity;

}