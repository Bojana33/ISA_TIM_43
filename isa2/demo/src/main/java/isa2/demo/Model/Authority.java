package isa2.demo.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.persistence.Entity;

@Entity
@Table(name="authority")
@Setter
public class Authority implements GrantedAuthority {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name="name")
    String name;

    @Override
    public String getAuthority() {
        return name;
    }

    @JsonIgnore
    public String getName() {
        return name;
    }

    @JsonIgnore
    public Integer getId() {
        return id;
    }

}