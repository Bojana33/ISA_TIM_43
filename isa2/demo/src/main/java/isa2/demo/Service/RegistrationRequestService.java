package isa2.demo.Service;

import isa2.demo.Model.RegistrationRequest;

import java.util.List;

public interface RegistrationRequestService {

    List<RegistrationRequest> findNotConfirmed();

    RegistrationRequest approveRequest(RegistrationRequest registrationRequest);
}
