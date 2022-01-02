package isa2.demo.Controller;

import isa2.demo.Model.Client;
import isa2.demo.Model.Reservation;
import isa2.demo.Service.ServiceImpl.UserComplaintServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping(value = "/user_complaint", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserComplaintController {

    public final UserComplaintServiceImpl userComplaintService;

    public UserComplaintController(UserComplaintServiceImpl userComplaintService){
        this.userComplaintService = userComplaintService;
    }

    @GetMapping("/get_list_of_reservations")
    public ResponseEntity<List<Reservation>> getListOfReservations(@RequestBody Client client){
        return new ResponseEntity<>(this.userComplaintService.createClientsList(client), HttpStatus.OK);
    }
}
