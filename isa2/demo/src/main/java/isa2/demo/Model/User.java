package isa2.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.persistence.Entity;
import java.io.Serializable;
import java.lang.*;
import java.security.Timestamp;
import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(unique = true,nullable = false)
   private Integer id;

   @Column(nullable = false)
   private String username;

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

   @Column(name = "last_password_reset_date")
   private Timestamp lastPasswordResetDate;
   
   @ManyToOne(cascade = CascadeType.ALL)
   private Address address;

   @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   private UserDeleteRequest userDeleteRequest;


   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
      return null;
   }

   @Override
   public String getUsername() {
      return null;
   }

   @Override
   public boolean isAccountNonExpired() {
      return false;
   }

   @Override
   public boolean isAccountNonLocked() {
      return false;
   }

   @Override
   public boolean isCredentialsNonExpired() {
      return false;
   }

   @Override
   public boolean isEnabled() {
      return false;
   }

}