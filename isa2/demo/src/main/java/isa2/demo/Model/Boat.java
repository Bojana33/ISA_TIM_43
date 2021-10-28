package isa2.demo.Model;

import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class Boat extends Entity {

    private BoatType type;

    private java.lang.Double length;

    private java.lang.String engineNumber;

    private java.lang.Double enginePower;

    private java.lang.Double maxSpeed;

    private java.lang.Set<NavigationEquipment> navigationEquipment;

    private java.lang.Double capacity;

    private java.lang.Collection<String> fishingEquipment;

    private java.lang.Double cancelationFee;

    private java.lang.String houseRules;


    public BoatOwner boatOwner;

}
