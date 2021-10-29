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
   @Column(unique = true, nullable = false)
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
   
   @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   private Period reservedPeriod;

   @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   private Period salePeriod;

   @OneToMany(fetch = FetchType.LAZY)
   private Collection<AdditionalService> additionalServices;

   @OneToOne(fetch = FetchType.LAZY)
   private OwnersReview ownersReview;

   @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   private ClientsReview clientsReview;

   @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   private UserComplaint userComplaint;

   @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
   private Client client;

   @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
   private isa2.demo.Model.Entity entity;
}