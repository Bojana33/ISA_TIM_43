package isa2.demo.Model;

import lombok.Data;

import javax.persistence.*;
import javax.persistence.Entity;

@Entity
@Data
@Table(name = "boats")
public class Boat extends isa2.demo.Model.Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "boat_id", unique = true, nullable = false)
    private Integer id;

    @Column
    private BoatType type;

    @Column
    private java.lang.Double length;

    @Column(name = "engine_number")
    private java.lang.String engineNumber;

    @Column(name = "engine_power")
    private java.lang.Double enginePower;

    @Column(name = "max_speed")
    private java.lang.Double maxSpeed;

    @Column
    private java.lang.Double capacity;


    @Column(name = "cancellation_fee")
    private java.lang.Double cancellationFee;

    @Column(name = "house_rules")
    private java.lang.String houseRules;

    @Column
    private String fishingEquipment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boat_owner_id", referencedColumnName = "boat_owner_id")
    public BoatOwner boatOwner;

    @ElementCollection
    private java.util.Set<NavigationEquipment> navigationEquipment;
}
