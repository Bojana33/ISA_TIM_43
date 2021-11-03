package isa2.demo.Service;

import isa2.demo.Model.User;
import isa2.demo.Model.UserRequest;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;

public interface UserService{
    Optional<User> findById(Integer id);
    User findByUsername(String username);
    List<User> findAll () throws AccessDeniedException;
    User save(UserRequest userRequest);
}
