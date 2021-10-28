package isa2.demo.Model;

import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class BoatOwner extends User {

    private java.lang.Double loyaltyPoints;

    private UserCategory category;


    public java.util.Collection<Boat> boat;

    public java.util.Collection<Reservation> reservation;
}
