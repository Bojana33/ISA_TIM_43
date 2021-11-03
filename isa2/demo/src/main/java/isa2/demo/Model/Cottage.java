package isa2.demo.Model;

import lombok.*;

import javax.persistence.*;

@javax.persistence.Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cottage extends Entity {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cottage")
    private java.util.Collection<Room> rooms;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "cottage_owner_id", referencedColumnName = "id")
    private CottageOwner cottageOwner;
}
