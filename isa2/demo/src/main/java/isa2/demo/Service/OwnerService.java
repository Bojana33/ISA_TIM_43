package isa2.demo.Service;

import isa2.demo.DTO.PeriodDTO;
import isa2.demo.Model.Entity;
import isa2.demo.Model.Owner;
import isa2.demo.Model.RegistrationRequest;

public interface OwnerService {
    Owner saveOwnerFromRequest(RegistrationRequest registrationRequest);
    Owner findById(Integer id);
    Owner findByEntity(Entity entity);
}
