package isa2.demo.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@javax.persistence.Entity
public class Room implements Serializable {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;

   @Column
   private Integer numberOfBeds;

   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "cottage_id",referencedColumnName = "id")
   @JsonBackReference
   private Cottage cottage;

}
