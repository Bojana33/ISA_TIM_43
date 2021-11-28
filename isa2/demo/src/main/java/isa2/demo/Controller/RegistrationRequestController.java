package isa2.demo.Controller;

import isa2.demo.Model.RegistrationRequest;
import isa2.demo.Service.RegistrationRequestService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/registration_request")
public class RegistrationRequestController {

    public final RegistrationRequestService registrationRequestService;

    public RegistrationRequestController(RegistrationRequestService registrationRequestService){
        this.registrationRequestService = registrationRequestService;
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/not_confirmed_requests")
    public List<RegistrationRequest> findNotConfirmed(){
        return this.registrationRequestService.findNotConfirmed();
    }

}
