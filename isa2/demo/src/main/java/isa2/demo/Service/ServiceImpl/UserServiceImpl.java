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

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;

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
    public User save(UserRequest userRequest) {
        User u = new User();
        u.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        u.setFirstName(userRequest.getFirstName());
        u.setSurname(userRequest.getSurname());
        u.setEmail(userRequest.getEmail());
        u.setPhoneNumber(userRequest.getPhoneNumber());
        u.setFirstLogIn(true);
        u.setDeleted(false);
        u.setIsAdmin(false);
        u.setActivated(false);

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
            auth = authService.findByname("ROLE_ADMIN");
        }

        if (userRequest.getUserType() == UserType.BOATOWNER || userRequest.getUserType() == UserType.COTTAGEOWNER || userRequest.getUserType() == UserType.INSTRUCTOR){
            RegistrationRequest registrationRequest = new RegistrationRequest();
            registrationRequest.setPassword(userRequest.getPassword());
            registrationRequest.setFirstName(userRequest.getFirstName());
            registrationRequest.setSurname(userRequest.getSurname());
            registrationRequest.setEmail(userRequest.getEmail());
            registrationRequest.setPhoneNumber(userRequest.getPhoneNumber());
            registrationRequest.setConfirmed(false);
            registrationRequest.setRegistrationExplanation(userRequest.getRegistrationExplanation());
            registrationRequest.setUserType(userRequest.getUserType());

            this.registrationRequestRepository.save(registrationRequest);

        }

        u.setAuthorities(auth);

        u = this.userRepository.save(u);
         return u;
    }

}
