package isa2.demo.Model;

import com.google.firebase.database.annotations.NotNull;
import lombok.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usersRequest")
public class UserRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true,nullable = false)
    private Integer id;

    @NotBlank(message = "Name is mandatory")
    @Size(min=2, max=30, message = "Invalid number of input characters")
    @Column(nullable = false)
    private String firstName;

    @NotBlank(message = "Surname is mandatory")
    @Size(min=2, max=30, message = "Invalid number of input characters")
    @Column(nullable = false)
    private String surname;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @NotNull
    @Size(min=5, max=30)
    @Column
    private String phoneNumber;

    @Column
    private String verificationCode;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "address_id")
    private Address address;
}
