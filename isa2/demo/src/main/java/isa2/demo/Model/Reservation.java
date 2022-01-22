package isa2.demo.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.persistence.Entity;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.lang.*;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reservations")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class Reservation implements Serializable {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(unique = true, nullable = false)
   private Integer id;

   @Column
   @CreatedDate
   private LocalDateTime creationDate;

   @Column
   private Double price;

   @Column
   private Double discount;

   @Column
   private Double ownersIncome;

   @Column(name = "number_of_guests")
   private Integer numberOfGuests;

   @Column(name = "reservation_status")
   private ReservationStatus reservationStatus = ReservationStatus.FREE;

   @Column(name = "additional_notes")
   private String additionalNotes;
   
   @OneToOne(fetch = FetchType.EAGER,  cascade = CascadeType.ALL, orphanRemoval = true)
   @JoinColumn(name = "reserved_period_id", referencedColumnName = "id")
   private Period reservedPeriod;

   public Period getReservedPeriod() {
      return reservedPeriod;
   }

   @OneToOne(fetch =FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
   @JoinColumn(name = "sale_period_id", referencedColumnName = "id")
   private Period salePeriod;

   @JsonManagedReference
   @OneToMany(fetch = FetchType.LAZY, mappedBy = "reservation", cascade = CascadeType.ALL)
   private Collection<AdditionalService> additionalServices;

   @OneToOne(fetch = FetchType.LAZY, mappedBy = "reservation", cascade = CascadeType.MERGE)
   private OwnersReview ownersReview;

   @OneToOne(fetch = FetchType.LAZY, mappedBy = "reservation", cascade = CascadeType.MERGE)
   private ClientsReview clientsReview;


   @OneToOne(fetch = FetchType.LAZY, mappedBy = "reservation", cascade = CascadeType.MERGE)
   private UserComplaint userComplaint;

   @JsonBackReference
   @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
   @JoinColumn(name = "client_id", referencedColumnName = "id")
   private Client client;

   @JsonBackReference
   @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
   @JoinColumn(name = "reservation_entity_id", referencedColumnName = "id")
   private isa2.demo.Model.Entity entity;

   public void setReservedPeriod(Period reservedPeriod) {
      this.reservedPeriod = reservedPeriod;
   }
}
