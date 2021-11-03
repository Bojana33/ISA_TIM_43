package isa2.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@javax.persistence.Entity
public class RegistrationRequest implements Serializable {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;

   @Column
   private String name;

   @Column
   private String surname;

   @Column
   private String password;

   @Column
   private String email;

   @Column
   private String phoneNumber;

   @Column
   private Boolean confirmed;

   @Column
   private UserType userType;

   @Column
   private String rejectionReason;
   
   @ManyToOne(cascade = CascadeType.ALL)
   @JoinColumn(name = "address_id", referencedColumnName = "address_id")
   private Address address;


}