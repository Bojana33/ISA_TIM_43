package isa2.demo.Model;

import lombok.Data;

import javax.persistence.*;

@javax.persistence.Entity
@Data
@Table(name = "clients")
public class Client extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "loyalty_points")
    private java.lang.Double loyaltyPoints;

    @Column
    private java.lang.Integer penalty;

    @Column
    private UserCategory category;

    @OneToMany
    public java.util.Collection<Reservation> reservation;

    @ManyToMany
    public java.util.Collection<Entity> subscriptions;

}

