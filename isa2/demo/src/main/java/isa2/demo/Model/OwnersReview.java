package isa2.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.persistence.Entity;
import java.io.Serializable;
import java.lang.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OwnersReview implements Serializable {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;

   @Column
   private String description;

   @Column
   private Double grade;

   @Column
   private ReviewStatus reviewStatus;

   @Column
   private Boolean isReported;
   
   @OneToOne(fetch = FetchType.EAGER, mappedBy = "ownersReview")
   private Reservation reservation;

}