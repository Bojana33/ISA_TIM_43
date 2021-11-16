package isa2.demo.Service.ServiceImpl;

import isa2.demo.Model.Authority;
import isa2.demo.Model.User;
import isa2.demo.Model.UserRequest;
import isa2.demo.Repository.UserRepository;
import isa2.demo.Repository.UserRequestRepository;
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
        u.setActivated(true);

        List<Authority> auth = authService.findByname("ROLE_USER");
        // u primeru se registruju samo obicni korisnici i u skladu sa tim im se i dodeljuje samo rola USER
        u.setAuthorities(auth);

        u = this.userRepository.save(u);
        return u;
    }

    @Override
    public UserRequest saveUserRequest(UserRequest userRequest) {
        UserRequest u = this.userRequestRepository.save(userRequest);
         return u;
    }

}
