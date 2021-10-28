package isa2.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.lang.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

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
   private Boolean activated;

   @Column
   private Boolean firstLogIn;

   @Column
   private Boolean deleted;

   @Column
   private Boolean isAdmin;
   
   @ManyToOne
   private Address address;

   @OneToOne
   private UserDeleteRequest userDeleteRequest;
   


}