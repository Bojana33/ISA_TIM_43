package isa2.demo.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.firebase.database.annotations.NotNull;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@javax.persistence.Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
@Table(name = "addresses")
public class Address implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id", unique = true, nullable = false)
    private Integer id;

    @NotBlank(message = "City is mandatory")
    @Size(min=3, max=30, message = "Invalid number of input characters")
    @Column
    private java.lang.String city;

    @NotBlank(message = "Country is mandatory")
    @Column
    private java.lang.String country;

    @NotBlank(message = "Street is mandatory")
    @Column
    private java.lang.String street;

    @NotNull
    @Column
    private java.lang.String houseNumber;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "address")
    private java.util.Set<User> users;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "address")
    private java.util.Collection<RegistrationRequest> registrationRequest;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "address")
    private java.util.Collection<UserRequest> userRequest;

    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY,mappedBy = "address")
    private Entity entity;

}
