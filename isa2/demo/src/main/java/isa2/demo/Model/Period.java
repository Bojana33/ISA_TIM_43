package isa2.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@javax.persistence.Entity
public class Period implements Serializable {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;

   @Column
   private LocalDateTime startDate;

   @Column
   private LocalDateTime endDate;

//   //TODO: NE ZNAM STA OVDE DA STAVIM ZA MAPPEDBY
//   @OneToOne(mappedBy = "reservedPeriod")
//   public Reservation reservation;
//
//   @OneToOne(mappedBy = "salePeriod")
//   public Reservation reservation1;

}