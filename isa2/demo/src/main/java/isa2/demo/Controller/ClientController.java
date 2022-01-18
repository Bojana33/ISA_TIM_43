package isa2.demo.Controller;

import isa2.demo.Config.ModelMapperConfig;
import isa2.demo.DTO.EntityDTO;
import isa2.demo.Model.Boat;
import isa2.demo.Model.Entity;
import isa2.demo.Model.Reservation;
import isa2.demo.Service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping(value = "/client", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClientController {

    private final ClientService clientService;
    private final ModelMapperConfig modelMapper;

    public ClientController(ModelMapperConfig modelMapper, ClientService clientService){
        this.clientService = clientService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/get_client_reservations")
    public ResponseEntity<Collection<Reservation>> getAll(Principal user){
        return new ResponseEntity<>(this.clientService.findAllReservations(user.getName()), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/getAllSubscriptions")
    public ResponseEntity<Collection<EntityDTO>> getAllSubscriptions(Principal user){
        Collection<Entity> entities = this.clientService.getAllSubscriptions(user.getName());
        Collection<EntityDTO> entityDTOS = new ArrayList<>();
        for(Entity entity : entities)
            entityDTOS.add(modelMapper.modelMapper().map(entity, EntityDTO.class));
        return new ResponseEntity<>(entityDTOS, HttpStatus.OK);
    }
}
