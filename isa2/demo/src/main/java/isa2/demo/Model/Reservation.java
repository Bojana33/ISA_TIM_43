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
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class Reservation implements Serializable {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(unique = true, nullable = false)
   private Integer id;

   @Column
   @CreatedDate
   private LocalDateTime creationDate;

   @Column
   private Float price;

   @Column(name = "number_of_guests")
   private Integer numberOfGuests;

   @Column(name = "reservation_status")
   private ReservationStatus reservationStatus = ReservationStatus.FREE;

   @Column(name = "additional_notes")
   private String additionalNotes;
   
   @OneToOne(cascade = CascadeType.ALL)
   @JoinColumn(name = "reserved_period_id", referencedColumnName = "id")
   private Period reservedPeriod;

   @OneToOne(cascade = CascadeType.ALL)
   @JoinColumn(name = "sale_period_id", referencedColumnName = "id")
   private Period salePeriod;

   @JsonManagedReference
   @OneToMany(fetch = FetchType.LAZY, mappedBy = "reservation", cascade = CascadeType.ALL)
   private Collection<AdditionalService> additionalServices;

   @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   @JoinColumn(name = "owners_review_id", referencedColumnName = "id")
   private OwnersReview ownersReview;

   @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   @JoinColumn(name = "client_review_id", referencedColumnName = "client_review_id")
   private ClientsReview clientsReview;

   @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   @JoinColumn(name = "user_complaint_id", referencedColumnName = "id")
   private UserComplaint userComplaint;

   @JsonBackReference
   @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
   @JoinColumn(name = "client_id", referencedColumnName = "id")
   private Client client;

   @JsonBackReference
   @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
   @JoinColumn(name = "reservation_entity_id", referencedColumnName = "id")
   private isa2.demo.Model.Entity entity;
}
