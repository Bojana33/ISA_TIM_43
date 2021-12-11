package isa2.demo.Model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
@Table(name = "periods")
public class Period implements Serializable {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;

   @Column(name = "start_date")
   private LocalDateTime startDate;

   @Column(name = "end_date")
   private LocalDateTime endDate;

   //TODO: NE ZNAM STA OVDE DA STAVIM ZA MAPPEDBY
   @OneToOne(mappedBy = "reservedPeriod")
   public Reservation reservation;
//
//   @OneToOne(mappedBy = "salePeriod")
//   public Reservation reservation1;

}
