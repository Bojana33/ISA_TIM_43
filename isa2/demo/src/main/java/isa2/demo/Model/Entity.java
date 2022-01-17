package isa2.demo.Model;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

@javax.persistence.Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "entities")
@Inheritance(strategy = InheritanceType.JOINED)
public class Entity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Integer id;

    @Column
    private java.lang.String name;
    @Column
    @Size(min = 5, max = 511)
    private java.lang.String description;
    @ElementCollection
    private java.util.Set<String> photos;
    @Column(name = "max_guests")
    private java.lang.Integer maxNumberOfGuests;
    @Column(name = "price_per_day")
    private java.lang.Double pricePerDay;

    @Column
    private java.lang.String entityPhoto;

    //TO DO: return this to transient, it is only a temporary column
    @Column(name = "average_grade")
    private java.lang.Double averageGrade = 0.0;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "address_id")
    private Address address;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "entity")
    private java.util.Collection<RentalTime> rentalTimes;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "entity")
    private java.util.Collection<AdditionalService> additionalServices;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "subscriptions")
    private java.util.Collection<Client> subscribedClients;


    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL , fetch = FetchType.LAZY, mappedBy = "entity", orphanRemoval = true)
    public java.util.Collection<Reservation> reservations;
}
