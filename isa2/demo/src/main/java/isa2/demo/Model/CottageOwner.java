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
public class CottageOwner extends User {

    @Column(name = "loyalty_points")
    private java.lang.Double loyaltyPoints;

    @Column
    private UserCategory category;

    @OneToMany(mappedBy = "cottageOwner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public java.util.Collection<Cottage> cottages;
}