package isa2.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.persistence.Entity;
import java.io.Serializable;
import java.lang.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements Serializable {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(unique = true,nullable = false)
   private Integer id;

   @Column(nullable = false)
   private String name;

   @Column(nullable = false)
   private String surname;

   @Column(nullable = false)
   private String password;

   @Column(nullable = false, unique = true)
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
   
   @ManyToOne(cascade = CascadeType.ALL)
   private Address address;

   @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   private UserDeleteRequest userDeleteRequest;
   


}