package isa2.demo.Model;

import javax.persistence.*;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
public class InstructorAvailability implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Period period;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private Owner owner;


}
