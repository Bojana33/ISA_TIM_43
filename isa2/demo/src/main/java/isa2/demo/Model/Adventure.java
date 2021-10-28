package isa2.demo.Model;

import lombok.Data;

import javax.persistence.*;
import javax.persistence.Entity;

@Entity
@Data
@Table(name = "advantures")
public class Adventure extends isa2.demo.Model.Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "advanture_id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "instructor_bio")
    private java.lang.String instructorBio;
    @Column(name = "cancellation_fee")
    private java.lang.Double cancellationFee;
    @Column(name = "house_rules")
    private java.lang.String houseRules;

}
