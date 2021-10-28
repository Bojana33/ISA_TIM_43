package isa2.demo.Model;

import lombok.Data;

import javax.persistence.*;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Data
@Table(name = "additionalServices")
public class AdditionalService implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "additinalServices_id", unique = true, nullable = false)
    private java.lang.Integer id;

    @Column
    private java.lang.String name;
    @Column
    private java.lang.Double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entity_id", referencedColumnName = "entity_id")
    public isa2.demo.Model.Entity entity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", referencedColumnName = "reservation_id")
    public Reservation reservation;
}
