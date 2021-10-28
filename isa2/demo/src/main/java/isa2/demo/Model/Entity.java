package isa2.demo.Model;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@javax.persistence.Entity
@Data
public class Entity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "entity_id", unique = true, nullable = false)
    private Integer id;

    @Column
    private java.lang.String name;
    @Column
    private java.lang.String description;
    @Column
    private java.util.Set<String> photos;
    @Column(name = "average_grade")
    private java.lang.Double averageGrade;
    @Column(name = "max_guests")
    private java.lang.Integer maxNumberOfGuests;
    @Column(name = "price_per_day")
    private java.lang.Double pricePerDay;

    @OneToOne
    public Address address;
    @OneToMany
    public java.util.Collection<RentalTime> rentalTime;

    @OneToMany
    public java.util.Collection<AdditionalService> additionalService;

    @ManyToMany
    public java.util.Collection<Client> subscribedClients;

}
