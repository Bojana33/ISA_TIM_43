package isa2.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;
import java.lang.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@javax.persistence.Entity
@Table(name = "user_complaints")
public class UserComplaint implements Serializable {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;

   @Column
   private String description;

   @Column
   private String response;

   @Column
   private Boolean processed;

   @OneToOne(fetch = FetchType.EAGER, mappedBy = "userComplaint")
   private Reservation reservation;

}