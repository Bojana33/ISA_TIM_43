package isa2.demo.Model;

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
@Entity
public class Room implements Serializable {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;

   @Column
   private Integer numberOfBeds;

   @ManyToOne
   private Cottage cottage;

}