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

    @OneToMany(mappedBy = "cottage", fetch = FetchType.LAZY)
    private java.util.Collection<Room> rooms;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private CottageOwner cottageOwner;
}
