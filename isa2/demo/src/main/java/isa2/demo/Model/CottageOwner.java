package isa2.demo.Model;

import lombok.Data;

import javax.persistence.*;
import javax.persistence.Entity;

@Entity
@Data
@Table(name = "cottage_owners")
public class CottageOwner extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cottage_owner_id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "loyalty_points")
    private java.lang.Double loyaltyPoints;

    @Column
    private UserCategory category;

    @OneToMany
    public java.util.Collection<Reservation> reservation;
    @OneToMany
    public java.util.Collection<Cottage> cottage;
}