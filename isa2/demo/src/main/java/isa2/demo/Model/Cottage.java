package isa2.demo.Model;

import lombok.Data;

import javax.persistence.*;
import javax.persistence.Entity;

@Entity
@Data
@Table(name = "cottages")
public class Cottage extends isa2.demo.Model.Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cottage_id", unique = true, nullable = false)
    private Integer id;

    @OneToMany
    public java.util.Collection<Room> room;
    @ManyToOne
    public CottageOwner cottageOwner;
}
