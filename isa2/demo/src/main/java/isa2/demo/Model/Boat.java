package isa2.demo.Model;

import lombok.*;

import javax.persistence.*;

@javax.persistence.Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "boats")
public class Boat extends Entity {

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

    @Column(name = "fishing_equipment")
    private String fishingEquipment;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "boat_owner_id", referencedColumnName = "id")
    public Owner owner;

    @ElementCollection
    private java.util.Set<NavigationEquipment> navigationEquipment;
}
