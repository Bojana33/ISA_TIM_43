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
   @JoinColumn(name = "reserved_period_id", referencedColumnName = "id")
   private Period reservedPeriod;

   @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   @JoinColumn(name = "sale_period_id", referencedColumnName = "id")
   private Period salePeriod;

   @OneToMany(fetch = FetchType.LAZY, mappedBy = "reservation")
   private Collection<AdditionalService> additionalServices;

   @OneToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "owners_review_id", referencedColumnName = "id")
   private OwnersReview ownersReview;

   @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   @JoinColumn(name = "client_review_id", referencedColumnName = "client_review_id")
   private ClientsReview clientsReview;

   @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   @JoinColumn(name = "user_complaint_id", referencedColumnName = "id")
   private UserComplaint userComplaint;

   @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
   @JoinColumn(name = "client_id", referencedColumnName = "id")
   private Client client;

   @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
   @JoinColumn(name = "reservation_entity_id", referencedColumnName = "id")
   private isa2.demo.Model.Entity entity;
}