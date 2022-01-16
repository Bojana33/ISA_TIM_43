package isa2.demo.Controller;

import isa2.demo.DTO.Mappers.UserComplaintMapper;
import isa2.demo.DTO.UserComplaintDTO;
import isa2.demo.Model.Client;
import isa2.demo.Model.Reservation;
import isa2.demo.Model.UserComplaint;
import isa2.demo.Service.UserComplaintService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value = "/user_complaint", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserComplaintController {

    public final UserComplaintService userComplaintService;

    public final UserComplaintMapper userComplaintMapper;


    public UserComplaintController(UserComplaintService userComplaintService, UserComplaintMapper userComplaintMapper){
        this.userComplaintService = userComplaintService;
        this.userComplaintMapper = userComplaintMapper;
    }

    @GetMapping("/get_list_of_reservations")
    public ResponseEntity<List<Reservation>> getListOfReservations(@RequestBody Client client){
        return new ResponseEntity<>(this.userComplaintService.createClientsList(client), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.CREATED)
    //@PreAuthorize("hasRole('USER')")
    @PostMapping(value = "/save_complaint")
    public UserComplaint createUserComplaint(@RequestBody UserComplaintDTO userComplaintDTO){
        UserComplaint userComplaint = this.userComplaintMapper.mapDtoToUserComplaint(userComplaintDTO);
        return this.userComplaintService.save(userComplaint);
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update_complaint")
    public ResponseEntity<UserComplaintDTO> updateUserComplaint(@RequestBody UserComplaintDTO userComplaintDTO){
        try {
            UserComplaint userComplaint = this.userComplaintMapper.mapDtoToUserComplaint(userComplaintDTO);
            userComplaint = this.userComplaintService.update(userComplaint);
            userComplaintDTO = this.userComplaintMapper.mapUserComplaintToDto(userComplaint);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(userComplaintDTO,HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get_complaints")
    public ResponseEntity<List<UserComplaintDTO>> getUnprocessedUserComplaints(){
        List<UserComplaint> userComplaints = this.userComplaintService.findAllUnprocessedComplaints();
        List<UserComplaintDTO> userComplaintDTOS = new ArrayList<>();
        for(UserComplaint userComplaint: userComplaints){
            userComplaintDTOS.add(this.userComplaintMapper.mapUserComplaintToDto(userComplaint));
        }
        return new ResponseEntity<>(userComplaintDTOS,HttpStatus.OK);
    }

    @PostMapping("/send_response")
    public void sendResponse(@RequestBody UserComplaintDTO userComplaintDTO){
        this.userComplaintService.sendResponse(this.userComplaintMapper.mapDtoToUserComplaint(userComplaintDTO));
    }

}
