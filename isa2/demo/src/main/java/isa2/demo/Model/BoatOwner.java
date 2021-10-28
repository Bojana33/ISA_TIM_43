package isa2.demo.Model;

import lombok.Data;

import javax.persistence.*;
import javax.persistence.Entity;

@Entity
@Data
@Table(name = "boat_owners")
public class BoatOwner extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "boat_owner_id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "loyalty_points")
    private java.lang.Double loyaltyPoints;

    @Column
    private UserCategory category;

    @OneToMany(cascade = CascadeType.ALL , fetch = FetchType.LAZY, mappedBy = "boatOwner")
    public java.util.Collection<Boat> boat;

    @OneToMany(cascade = CascadeType.ALL , fetch = FetchType.LAZY, mappedBy = "boatOwner")
    public java.util.Collection<Reservation> reservation;
}
