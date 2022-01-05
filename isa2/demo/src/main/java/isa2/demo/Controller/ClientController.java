package isa2.demo.Controller;

import isa2.demo.Model.Boat;
import isa2.demo.Model.Reservation;
import isa2.demo.Service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping(value = "/client", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClientController {

    @Autowired
    ClientService clientService;

    @GetMapping("/get_client_reservations")
    public ResponseEntity<Collection<Reservation>> getAll(Principal user){
        return new ResponseEntity<>(this.clientService.findAllReservations(user.getName()), HttpStatus.OK);
    }
}
