package isa2.demo.Service.ServiceImpl;

import isa2.demo.Model.RegistrationRequest;
import isa2.demo.Repository.RegistrationRequestRepository;
import isa2.demo.Service.OwnerService;
import isa2.demo.Service.RegistrationRequestService;
import isa2.demo.Service.UserService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.List;

@Service
public class RegistrationRequestServiceImpl implements RegistrationRequestService {

    public final RegistrationRequestRepository registrationRequestRepository;

    public final UserService userService;

    public final PasswordEncoder passwordEncoder;

    public final OwnerService ownerService;

    public RegistrationRequestServiceImpl(RegistrationRequestRepository registrationRequestRepository,
                                          UserService userService,
                                          PasswordEncoder passwordEncoder,
                                          OwnerService ownerService){
        this.ownerService = ownerService;
        this.registrationRequestRepository = registrationRequestRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<RegistrationRequest> findNotConfirmed() {
        List<RegistrationRequest> requests = this.registrationRequestRepository.findAllByConfirmed(false);
        return requests;
    }

    @Override
    public void approveRequest(Integer id) {
        RegistrationRequest request = this.registrationRequestRepository.findById(id).get();
        request.setConfirmed(true);
        this.ownerService.saveOwnerFromRequest(request);
        String subject = "Request approved";
        String content = "Dear " + request.getFirstName() + " " + request.getSurname() + ",<br>" + "Your registration request is approved";
        try {
            this.userService.sendEmail(subject,content);
        } catch (MessagingException me){
            System.out.println("Message exception");
        }
        this.registrationRequestRepository.delete(request);
    }



    @Override
    public RegistrationRequest getOne(Integer id) {
        RegistrationRequest request = this.registrationRequestRepository.findById(id).get();
        return request;
    }

    @Override
    public void rejectRequest(Integer id, String rejectionReason) {
        RegistrationRequest request = this.registrationRequestRepository.findById(id).get();
        request.setRejectionReason(rejectionReason);
        this.registrationRequestRepository.save(request);
        String subject = "Request rejected";
        String content = "Dear " + request.getFirstName() + " " + request.getSurname() + ",<br>" + request.getRejectionReason();
        try {
            this.userService.sendEmail(subject,content);
        } catch (MessagingException me){
            System.out.println("Message exception");
        }
        this.registrationRequestRepository.delete(request);
    }

    @Override
    public RegistrationRequest save(RegistrationRequest registrationRequest) {
        registrationRequest.setConfirmed(false);
        return this.registrationRequestRepository.save(registrationRequest);
    }

    @Override
    public RegistrationRequest findByEmail(String email) throws UsernameNotFoundException {
        RegistrationRequest registrationRequest = registrationRequestRepository.findByEmail(email);
        return registrationRequest;
    }
}
