package isa2.demo.Model;

import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class Adventure extends Entity {

    private java.lang.String instructorBio;

    private java.lang.Double cancelationFee;

    private java.lang.String houseRules;

}
