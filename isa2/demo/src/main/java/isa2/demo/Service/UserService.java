package isa2.demo.Service;

import isa2.demo.Model.User;
import isa2.demo.Model.UserRequest;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;

public interface UserService{
    Optional<User> findById(Integer id);
    User findByEmail(String email);
    List<User> findAll () throws AccessDeniedException;
    User save(UserRequest userRequest);
    UserRequest saveUserRequest(UserRequest userRequest) throws MessagingException;
    void sendVerificationEmail(UserRequest userRequest) throws AddressException, MessagingException;
    boolean verify(String verificationCode);
}
