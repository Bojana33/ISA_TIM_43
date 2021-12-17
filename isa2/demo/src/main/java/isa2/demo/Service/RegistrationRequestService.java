package isa2.demo.Service;

import isa2.demo.Model.RegistrationRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface RegistrationRequestService {

    List<RegistrationRequest> findNotConfirmed();

    RegistrationRequest approveRequest(Integer id);

    RegistrationRequest getOne(Integer id);

    void rejectRequest(Integer id, String rejectionReason);

    RegistrationRequest save(RegistrationRequest registrationRequest);

    RegistrationRequest findByEmail(String email) throws UsernameNotFoundException;
}
