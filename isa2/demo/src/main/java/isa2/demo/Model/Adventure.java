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

    @Column(name = "instructor_bio")
    private java.lang.String instructorBio;
    @Column(name = "cancellation_fee")
    private java.lang.Double cancellationFee;
    @Column(name = "house_rules")
    private java.lang.String houseRules;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "instructor_id", referencedColumnName = "id")
    private Instructor instructor;
    @Column
    private String defaultFishingEquipment;

}
