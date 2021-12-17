package isa2.demo.Model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.persistence.Entity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "ownerType", discriminatorType=DiscriminatorType.STRING)
public class Owner extends User{
    @Column(name = "loyalty_points")
    private java.lang.Double loyaltyPoints;

    @Column
    private UserCategory category;

    @OneToMany(cascade = CascadeType.ALL , fetch = FetchType.LAZY, mappedBy = "owner")
    public java.util.Collection<Boat> boat;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "owner")
    public java.util.Collection<Cottage> cottages;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "owner")
    public java.util.Collection<Adventure> adventures;

    private OwnerType ownerType;
}
