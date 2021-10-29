package isa2.demo.Model;

import lombok.*;

import javax.persistence.*;
import javax.persistence.Entity;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoatOwner extends User {

    @Column(name = "loyalty_points")
    private java.lang.Double loyaltyPoints;

    @Column
    private UserCategory category;

    @OneToMany(cascade = CascadeType.ALL , fetch = FetchType.LAZY, mappedBy = "boatOwner")
    public java.util.Collection<Boat> boat;

}
