package isa2.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
public class Reservation implements Serializable {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "reservation_id", unique = true, nullable = false)
   private Integer id;

   @Column
   private LocalDateTime creationDate;

   @Column
   private Float price;

   @Column
   private Integer numberOfGuests;

   @Column
   private ReservationStatus reservationStatus;

   @Column
   private String additionalNotes;
   
   @OneToOne
   private Period reservedPeriod;

   @OneToOne
   private Period salePeriod;

   @OneToMany
   private Collection<AdditionalService> additionalService;

   @OneToOne
   private OwnersReview ownersReview;

   @OneToOne
   private ClientsReview clientsReview;

   @OneToOne
   private UserComplaint userComplaint;

   @ManyToOne
   private Client client;

   @ManyToOne
   private Instructor instructor;

   @ManyToOne
   private CottageOwner cottageOwner;

   @ManyToOne
   private BoatOwner boatOwner;
   
   
   
   
   
   
   
   
  
   
   
   
   
   

}