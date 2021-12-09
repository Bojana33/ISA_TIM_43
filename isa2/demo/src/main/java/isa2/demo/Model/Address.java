package isa2.demo.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@javax.persistence.Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
    private java.lang.String houseNumber;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "address")
    private java.util.Set<User> users;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "address")
    private java.util.Collection<RegistrationRequest> registrationRequest;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "address")
    private java.util.Collection<UserRequest> userRequest;

    @OneToOne(fetch = FetchType.LAZY,mappedBy = "address")
    private Entity entity;

}
