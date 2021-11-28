package isa2.demo.Service;

import isa2.demo.Model.User;
import isa2.demo.Model.UserRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;

public interface UserService{
    Optional<User> findById(Integer id);
    User findByEmail(String email) throws UsernameNotFoundException;
    List<User> findAll () throws AccessDeniedException;
    User save(UserRequest userRequest);
}
