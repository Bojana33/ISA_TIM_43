package isa2.demo.Model;
import lombok.*;

import javax.persistence.*;
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
    private java.lang.String description;
    @ElementCollection
    private java.util.Set<String> photos;
    @Column(name = "average_grade")
    private java.lang.Double averageGrade;
    @Column(name = "max_guests")
    private java.lang.Integer maxNumberOfGuests;
    @Column(name = "price_per_day")
    private java.lang.Double pricePerDay;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "address_id")
    private Address address;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "entity")
    private java.util.Collection<RentalTime> rentalTimes;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "entity")
    private java.util.Collection<AdditionalService> additionalServices;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "subscriptions")
    private java.util.Collection<Client> subscribedClients;

    @OneToMany(cascade = CascadeType.ALL , fetch = FetchType.LAZY, mappedBy = "entity")
    public java.util.Collection<Reservation> reservations;
}
