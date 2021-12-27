package isa2.demo.Service;

import isa2.demo.Exception.EmailAlreadyInUseException;
import isa2.demo.Exception.EmailNotExistsException;
import isa2.demo.Model.RegistrationRequest;
import isa2.demo.Model.User;
import isa2.demo.Model.UserRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;

public interface UserService{
    User findById(Integer id);
    User findByEmail(String email) throws UsernameNotFoundException;
    List<User> findAll () throws AccessDeniedException;
    User save(UserRequest userRequest);
    UserRequest saveUserRequest(UserRequest userRequest) throws MessagingException, EmailAlreadyInUseException;
    void sendVerificationEmail(UserRequest userRequest) throws AddressException, MessagingException;
    boolean verify(String verificationCode);
    User findByUsername(String email) throws UsernameNotFoundException;
    User updateUser(User userUpdate, String user) throws EmailNotExistsException;
    void sendEmail(String subject, String content, String email) throws AddressException, MessagingException;
    void delete(User user);
}
