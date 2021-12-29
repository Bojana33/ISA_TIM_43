package isa2.demo.Service.ServiceImpl;

import isa2.demo.Exception.EmailAlreadyInUseException;
import isa2.demo.Model.Authority;
import isa2.demo.Model.User;
import isa2.demo.Model.UserRequest;
import isa2.demo.Model.*;
import isa2.demo.Repository.RegistrationRequestRepository;
import isa2.demo.Repository.UserRepository;
import isa2.demo.Repository.UserRequestRepository;
import isa2.demo.Service.AuthorityService;
import isa2.demo.Service.UserService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.*;
import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    private UserRequestRepository userRequestRepository;



    @Override
    public User findByEmail(String email) throws UsernameNotFoundException {
        User u = userRepository.findByEmail(email);
        return u;
    }

    public User findById(Integer id) {
//        Optional<User> u = Optional.ofNullable(userRepository.findById(id).orElse(null));
//        return u;
        User u = this.userRepository.findById(id).get();
        return u;
    }

    public List<User> findAll() throws AccessDeniedException {
        List<User> result = userRepository.findAll();
        return result;
    }

    @Override
    public User save(UserRequest userRequest) {
        return null;
    }

    @Override
    public UserRequest saveUserRequest(UserRequest userRequest) throws MessagingException, EmailAlreadyInUseException {
        try {
            String randomCode = RandomString.make(64);
            userRequest.setVerificationCode(randomCode);
            sendVerificationEmail(userRequest);
        }catch (MessagingException me) {
            System.out.println("Message exception");
        }

        if (userRepository.findByEmail(userRequest.getEmail()) != null || userRequestRepository.findByEmail(userRequest.getEmail()) != null) {
            throw new EmailAlreadyInUseException("Email already in use");
        }
        UserRequest u = this.userRequestRepository.save(userRequest);
        return u;
    }

    @Override
    public void sendVerificationEmail(UserRequest userRequest) throws AddressException, MessagingException {
        // send activation mail
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

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(userRequest.getEmail()));
            msg.setSubject("You created account on our portal!");

            String subject = "Please verify your registration";
            String verifyURL = "localhost:8090" + "/verify?code=" + userRequest.getVerificationCode();
            String content = "Dear " + userRequest.getFirstName() + ",<br>"
                    + "Please click the link below to verify your registration" + "</p><br><a href='http://localhost:8090/auth/verify/" + userRequest.getVerificationCode() +
            "'> VERIFY</a>";

            msg.setSubject(subject);
            msg.setContent(content, "text/html");
            Transport.send(msg);
        } catch (AddressException ae) {
            System.out.println("Address exception");
        } catch (MessagingException me) {
            System.out.println("Message exception");
        }

    }
    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }

    @Override
    public boolean verify(String verificationCode) {
        UserRequest userRequest = userRequestRepository.findByVerificationCode(verificationCode);
        if (userRequest == null || userRequest.getVerificationCode() == null) {
            return false;
        } else {
            //Create user
            User user = new User();
            user.setFirstName(userRequest.getFirstName());
            user.setSurname(userRequest.getSurname());
            user.setPassword(userRequest.getPassword());
            user.setEmail(userRequest.getEmail());
            user.setPhoneNumber(userRequest.getPhoneNumber());
            user.setAddress(userRequest.getAddress());
            user.setActivated(true);
            user.setDeleted(false);
            user.setIsAdmin(false);
            userRepository.save(user);

            //disable ability to verified 2 times same account
            userRequest.setVerificationCode(null);
            userRequestRepository.save(userRequest);
            return true;
        }

    }
    @Override
    public void sendEmail(String subject, String content, String email) throws AddressException, MessagingException {
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

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));

            msg.setSubject(subject);
            msg.setContent(content, "text/html");
            Transport.send(msg);
        } catch (AddressException ae) {
            System.out.println("Address exception");
        } catch (MessagingException me) {
            System.out.println("Message exception");
        }
    }

    @Override
    public void delete(User user) {
        this.userRepository.delete(user);
    }
}
