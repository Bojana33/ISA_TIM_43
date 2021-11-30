package isa2.demo.Service.ServiceImpl;

import isa2.demo.Model.*;
import isa2.demo.Repository.RegistrationRequestRepository;
import isa2.demo.Repository.UserRepository;
import isa2.demo.Service.AuthorityService;
import isa2.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RegistrationRequestRepository registrationRequestRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthorityService authService;

    @Override
    public User findByEmail(String email) throws UsernameNotFoundException {
        User u = userRepository.findByEmail(email);
        return u;
    }

    public Optional<User> findById(Integer id) {
        Optional<User> u = Optional.ofNullable(userRepository.findById(id).orElse(null));
        return u;
    }

    public List<User> findAll() throws AccessDeniedException {
        List<User> result = userRepository.findAll();
        return result;
    }

    @Override
    public User save(RegistrationRequest userRequest) {
        User u = new User();
        u.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        u.setFirstName(userRequest.getFirstName());
        u.setSurname(userRequest.getSurname());
        u.setEmail(userRequest.getEmail());
        u.setPhoneNumber(userRequest.getPhoneNumber());
        u.setFirstLogIn(true);
        u.setDeleted(false);
        u.setIsAdmin(false);
        u.setActivated(true);

        List<Authority> auth;

        if (userRequest.getUserType() == UserType.CLIENT){
             auth = authService.findByname("ROLE_CLIENT");
        }
        else if (userRequest.getUserType() == UserType.COTTAGEOWNER){
            auth = authService.findByname("ROLE_COTTAGEOWNER");
        } else
        if (userRequest.getUserType() == UserType.BOATOWNER){
            auth = authService.findByname("ROLE_BOATOWNER");
        } else
        if (userRequest.getUserType() == UserType.INSTRUCTOR){
            auth = authService.findByname("ROLE_INSTRUCTOR");
        } else{
            u.setIsAdmin(true);
            auth = authService.findByname("ROLE_ADMIN");
        }

        //auth.add(this.authService.findByName("ROLE_USER"));
        u.setAuthorities(auth);

        u = this.userRepository.save(u);
         return u;
    }

    @Override
    public void sendEmail(RegistrationRequest registrationRequest, String subject, String content) throws AddressException, MessagingException {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("inisatim43@gmail.com", "bajicb182075");
                }
            });

            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("inisatim43@gmail.com", false));

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(registrationRequest.getEmail()));

            msg.setSubject(subject);
            msg.setContent(content, "text/html");
            Transport.send(msg);
        } catch (AddressException ae) {
            System.out.println("Address exception");
        } catch (MessagingException me) {
            System.out.println("Message exception");
        }
    }

}
