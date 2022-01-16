package isa2.demo.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@javax.persistence.Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "additional_services")
public class AdditionalService implements Serializable {

    //TODO: verovatno treba da se doda state kako bi znali da li je klijent selektovao dodatnu uslugu
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "additional_services_id", unique = true, nullable = false)
    private java.lang.Integer id;

    @Column
    private java.lang.String name;
    @Column
    private java.lang.Double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entity_id", referencedColumnName = "id")
    @JsonBackReference
    public Entity entity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", referencedColumnName = "id")
    @JsonBackReference
    public Reservation reservation;
}
