package isa2.demo.Model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.firebase.database.annotations.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.lang.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class User implements UserDetails {

   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(unique = true,nullable = false)
   private Integer id;

   @NotBlank(message = "Name is mandatory")
   @Size(min=2, max=30, message = "Invalid number of input characters")
   @Column
   private String firstName;

   @NotBlank(message = "Surname is mandatory")
   @Size(min=2, max=30, message = "Invalid number of input characters")
   @Column
   private String surname;

   @JsonIgnore
   @Column(nullable = false)
   private String password;

   @NotBlank(message = "Email is mandatory")
   @Size(min=5, max=50, message = "Invalid number of input characters")
   @Column(nullable = false, unique = true)
   private String email;

   @NotBlank(message = "Phone number is mandatory")
   @Size(min=5, max=30, message = "Invalid number of input characters")
   @Column
   private String phoneNumber;

   @Column
   private Boolean activated;

   @Column
   private Boolean firstLogin;

   @Column
   private Boolean deleted;

   @Column
   private Boolean isAdmin;

   @Column(name = "last_password_reset_date")
   private Timestamp lastPasswordResetDate;
   
   @ManyToOne(cascade = CascadeType.ALL)
   @JoinColumn(name = "address_id", referencedColumnName = "address_id")
   private Address address;

   @OneToOne(fetch = FetchType.LAZY,  mappedBy = "user")
   private UserDeleteRequest userDeleteRequest;

   @ManyToMany(fetch = FetchType.EAGER)
   @JoinTable(name = "user_authority",
           joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
           inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
   private List<Authority> authorities;

   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
      return this.authorities;
   }

   @Override
   public String getUsername() {
      return this.email;
   }

   public void setPassword(String password) {
      Timestamp now = new Timestamp(new Date().getTime());
      this.setLastPasswordResetDate(now);
      this.password = password;
   }

   @JsonIgnore
   @Override
   public boolean isAccountNonExpired() {
      return true;
   }

   @JsonIgnore
   @Override
   public boolean isAccountNonLocked() {
      return true;
   }

   @JsonIgnore
   @Override
   public boolean isCredentialsNonExpired() {
      return true;
   }

   @Override
   public boolean isEnabled() {
      return this.activated;
   }
}
