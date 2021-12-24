package isa2.demo.Controller;

import isa2.demo.DTO.Mappers.RegistrationRequestMapper;
import isa2.demo.DTO.RegistrationRequestDTO;
import isa2.demo.Model.RegistrationRequest;
import isa2.demo.Service.RegistrationRequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/registration_request")
public class RegistrationRequestController {

    public final RegistrationRequestService registrationRequestService;
    public final RegistrationRequestMapper registrationRequestMapper;

    public RegistrationRequestController(RegistrationRequestService registrationRequestService,
                                         RegistrationRequestMapper registrationRequestMapper){
        this.registrationRequestMapper = registrationRequestMapper;
        this.registrationRequestService = registrationRequestService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/not_confirmed_requests")
    public List<RegistrationRequestDTO> findNotConfirmed(){
        List<RegistrationRequest> registrationRequestList = this.registrationRequestService.findNotConfirmed();
        List<RegistrationRequestDTO> registrationRequestDTOS = new ArrayList<>();
        if(registrationRequestList != null){
            for(RegistrationRequest registrationRequest: registrationRequestList){
                registrationRequestDTOS.add(registrationRequestMapper.mapRegistrationRequestToDto(registrationRequest));
            }
        }
        return registrationRequestDTOS;


    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/approve_request/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void approveRequest(@PathVariable Integer id){
        this.registrationRequestService.approveRequest(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/get_request/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegistrationRequest> getOne(@PathVariable Integer id){
        return new ResponseEntity<>(this.registrationRequestService.getOne(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/reject_request/{id}")
    public void  rejectRequest(@PathVariable Integer id,@RequestBody String rejectionReason){
        this.registrationRequestService.rejectRequest(id,rejectionReason);
    }

}
