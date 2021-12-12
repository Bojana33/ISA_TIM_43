package isa2.demo.Service.ServiceImpl;

import isa2.demo.Model.*;
import isa2.demo.Repository.OwnerRepository;
import isa2.demo.Service.AuthorityService;
import isa2.demo.Service.OwnerService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OwnerServiceImpl implements OwnerService {


    public final OwnerRepository ownerRepository;
    public final PasswordEncoder passwordEncoder;
    public final AuthorityService authorityService;

    public OwnerServiceImpl(OwnerRepository ownerRepository,
                            PasswordEncoder passwordEncoder,
                            AuthorityService authorityService) {
        this.ownerRepository = ownerRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityService = authorityService;
    }

    @Override
    public Owner saveOwnerFromRequest(RegistrationRequest registrationRequest) {
        Owner owner = new Owner();
        owner.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        owner.setFirstName(registrationRequest.getFirstName());
        owner.setSurname(registrationRequest.getSurname());
        owner.setEmail(registrationRequest.getEmail());
        owner.setPhoneNumber(registrationRequest.getPhoneNumber());
        owner.setAddress(registrationRequest.getAddress());
        owner.setFirstLogIn(true);
        owner.setDeleted(false);
        owner.setIsAdmin(false);
        owner.setActivated(true);
        owner.setLoyaltyPoints(0.0);
        owner.setCategory(UserCategory.REGULAR);

        List<Authority> auth;
        if (registrationRequest.getUserType() == UserType.COTTAGEOWNER){
            auth = authorityService.findByname("ROLE_COTTAGEOWNER");
            owner.setOwnerType(OwnerType.COTTAGEOWNER);
        } else if (registrationRequest.getUserType() == UserType.BOATOWNER){
            auth = authorityService.findByname("ROLE_BOATOWNER");
            owner.setOwnerType(OwnerType.BOATOWNER);
        } else {
            auth = authorityService.findByname("ROLE_INSTRUCTOR");
            owner.setOwnerType(OwnerType.INSTRUCTOR);
        }

        owner.setAuthorities(auth);
        ownerRepository.save(owner);
        return owner;
    }
}
