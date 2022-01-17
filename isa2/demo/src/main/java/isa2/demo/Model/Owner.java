package isa2.demo.Model;
import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "ownerType", discriminatorType=DiscriminatorType.STRING)
public class Owner extends User{
    @Column(name = "loyalty_points")
    private java.lang.Double loyaltyPoints;

    @Column
    private UserCategory category;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL , fetch = FetchType.LAZY, mappedBy = "owner")
    public java.util.Collection<Boat> boat;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "owner")
    public java.util.Collection<Cottage> cottages;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "owner")
    public java.util.Collection<Adventure> adventures;

    @Column
    private OwnerType ownerType;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "owner")
    public java.util.Collection<InstructorAvailability> availabilityPeriods = new ArrayList<>();




}
