package isa2.demo.Model;

import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class AdditionalService {

    private java.lang.Integer id;

    private java.lang.String name;

    private java.lang.Double price;


    public Entity entity;

    public Reservation reservation;

}
