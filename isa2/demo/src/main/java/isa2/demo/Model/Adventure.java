package isa2.demo.Model;

import lombok.*;

import javax.persistence.*;

@javax.persistence.Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Adventure extends Entity {

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "adventure_owner_id", referencedColumnName = "id")
    private Owner owner;

    @Column(name = "instructor_bio")
    private java.lang.String instructorBio;
    @Column(name = "cancellation_fee")
    private java.lang.Double cancellationFee;
    @Column(name = "house_rules")
    private java.lang.String houseRules;
    @Column
    private String defaultFishingEquipment;

}
