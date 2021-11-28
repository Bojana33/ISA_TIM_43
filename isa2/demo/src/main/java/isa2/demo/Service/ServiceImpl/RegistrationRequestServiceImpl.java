package isa2.demo.Service.ServiceImpl;

import isa2.demo.Model.RegistrationRequest;
import isa2.demo.Repository.RegistrationRequestRepository;
import isa2.demo.Service.RegistrationRequestService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistrationRequestServiceImpl implements RegistrationRequestService {

    public final RegistrationRequestRepository registrationRequestRepository;

    public RegistrationRequestServiceImpl(RegistrationRequestRepository registrationRequestRepository){
        this.registrationRequestRepository = registrationRequestRepository;
    }

    @Override
    public List<RegistrationRequest> findNotConfirmed() {
        List<RegistrationRequest> requests = this.registrationRequestRepository.findAllByConfirmed(false);
        return requests;
    }

    @Override
    public RegistrationRequest approveRequest(RegistrationRequest registrationRequest) {
        RegistrationRequest request = this.registrationRequestRepository.getById(registrationRequest.getId());
        request.setConfirmed(true);
        return this.registrationRequestRepository.save(request);
    }
}
