package isa2.demo.Model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.persistence.Entity;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "registration_request")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class RegistrationRequest implements Serializable {

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Integer id;

   @Column
   private String firstName;

   @Column
   private String surname;

   @Column
   private String password;

   @Column
   private String email;

   @Column
   private String phoneNumber;

   @Column
   private boolean confirmed;

   @Column
   private String registrationExplanation;

   @Column
   private OwnerType ownerType;

   @Column
   private String rejectionReason;
   
   @ManyToOne(cascade = CascadeType.ALL)
   @JoinColumn(name = "address_id", referencedColumnName = "address_id")
   private Address address;


}
