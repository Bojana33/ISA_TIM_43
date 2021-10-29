package isa2.demo.Model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@javax.persistence.Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
    @JoinColumn(name = "entity_id", referencedColumnName = "id")
    public Entity entity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", referencedColumnName = "id")
    public Reservation reservation;
}
