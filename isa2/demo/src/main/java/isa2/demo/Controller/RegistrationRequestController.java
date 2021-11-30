package isa2.demo.Controller;

import isa2.demo.Model.RegistrationRequest;
import isa2.demo.Service.RegistrationRequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/approve_request/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegistrationRequest> approveRequest(@PathVariable Integer id){
        return new ResponseEntity<>(this.registrationRequestService.approveRequest(id), HttpStatus.OK);
    }

    @GetMapping(value = "/get_request/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegistrationRequest> getOne(@PathVariable Integer id){
        return new ResponseEntity<>(this.registrationRequestService.getOne(id), HttpStatus.OK);
    }

    @PostMapping(value = "/reject_request/{id}")
    public void  rejectRequest(@PathVariable Integer id,@RequestBody RegistrationRequest registrationRequest){
        this.registrationRequestService.rejectRequest(id,registrationRequest);
    }

}
