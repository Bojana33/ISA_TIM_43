package isa2.demo.Service;

import isa2.demo.Model.Owner;
import isa2.demo.Model.RegistrationRequest;

public interface OwnerService {
    Owner saveOwnerFromRequest(RegistrationRequest registrationRequest);
}
