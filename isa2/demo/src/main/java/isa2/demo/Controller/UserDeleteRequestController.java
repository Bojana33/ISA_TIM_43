package isa2.demo.Controller;

import isa2.demo.DTO.Mappers.UserDeleteRequestMapper;
import isa2.demo.DTO.UserDeleteRequestDTO;
import isa2.demo.Model.UserDeleteRequest;
import isa2.demo.Service.UserDeleteRequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/user_delete_request", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserDeleteRequestController {

    public final UserDeleteRequestService userDeleteRequestService;

    public final UserDeleteRequestMapper userDeleteRequestMapper;

    public UserDeleteRequestController(UserDeleteRequestService userDeleteRequestService, UserDeleteRequestMapper userDeleteRequestMapper){
        this.userDeleteRequestService = userDeleteRequestService;
        this.userDeleteRequestMapper = userDeleteRequestMapper;
    }

    @PostMapping(value = "/add_delete_request",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('COTTAGEOWNER','BOATOWNER','INSTRUCTOR','ADMIN', 'CLIENT')")
    public ResponseEntity<UserDeleteRequestDTO> addRequest(@RequestBody UserDeleteRequestDTO userDeleteRequestDTO) throws Exception {
        UserDeleteRequest userDeleteRequest = userDeleteRequestMapper.mapDtoToUserDeleteRequest(userDeleteRequestDTO);
        UserDeleteRequest existUserDeleteRequest = this.userDeleteRequestService.findByUser(userDeleteRequest.getUser());
        if (existUserDeleteRequest != null){
            throw new Exception("Request is already sent");
        }
        this.userDeleteRequestService.save(userDeleteRequest);
        UserDeleteRequestDTO userDeleteRequestDTO1 = this.userDeleteRequestMapper.mapUserDeleteRequestToDto(userDeleteRequest);
        return new ResponseEntity<>(userDeleteRequestDTO1, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/approve_request", produces = MediaType.APPLICATION_JSON_VALUE)
    public void approveRequest(@RequestBody UserDeleteRequestDTO userDeleteRequestDTO){
        UserDeleteRequest userDeleteRequest = this.userDeleteRequestMapper.mapDtoToUserDeleteRequest(userDeleteRequestDTO);
        this.userDeleteRequestService.approveRequest(userDeleteRequest);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/reject_request")
    public void  rejectRequest(@RequestBody UserDeleteRequestDTO userDeleteRequestDTO){
        UserDeleteRequest userDeleteRequest = this.userDeleteRequestMapper.mapDtoToUserDeleteRequest(userDeleteRequestDTO);
        this.userDeleteRequestService.rejectRequest(userDeleteRequest);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/on_hold_requests")
    public List<UserDeleteRequestDTO> findRequestsOnHold() {
        List<UserDeleteRequest> userDeleteRequestList = this.userDeleteRequestService.findAll();
        List<UserDeleteRequestDTO> userDeleteRequestDTOS = new ArrayList<>();
        if (userDeleteRequestList != null) {
            for (UserDeleteRequest userDeleteRequest : userDeleteRequestList) {
                userDeleteRequestDTOS.add(userDeleteRequestMapper.mapUserDeleteRequestToDto(userDeleteRequest));
            }
        }
        return userDeleteRequestDTOS;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get_request/{id}")
    public ResponseEntity<UserDeleteRequestDTO> getRequest(@PathVariable Integer id){
        UserDeleteRequest userDeleteRequest = this.userDeleteRequestService.getRequestById(id);
        UserDeleteRequestDTO userDeleteRequestDTO = this.userDeleteRequestMapper.mapUserDeleteRequestToDto(userDeleteRequest);
        return new ResponseEntity<>(userDeleteRequestDTO,HttpStatus.OK);
    }
}
