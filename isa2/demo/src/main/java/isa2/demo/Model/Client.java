package isa2.demo.Model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;

@javax.persistence.Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Inheritance(strategy = InheritanceType.JOINED)
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class Client extends User {

    @Column(name = "loyalty_points")
    private java.lang.Double loyaltyPoints;

    @Column
    private java.lang.Integer penalty;

    @Column
    private UserCategory category;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "client")
    public java.util.Collection<Reservation> reservation;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "client_subscriptions", joinColumns = @JoinColumn(name = "client_id", referencedColumnName = "id")
            , inverseJoinColumns = @JoinColumn(name = "entity_id", referencedColumnName = "id"))
    public java.util.Collection<Entity> subscriptions;

}

