package isa2.demo.Controller;

import isa2.demo.Config.ModelMapperConfig;
import isa2.demo.DTO.AdventureDTO;
import isa2.demo.DTO.RentalTimeDTO;
import isa2.demo.DTO.ReservationDTO;
import isa2.demo.Model.Adventure;
import isa2.demo.Model.RentalTime;
import isa2.demo.Model.Reservation;
import isa2.demo.Service.ClientService;
import isa2.demo.Service.EntityService;
import isa2.demo.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.persistence.criteria.CriteriaBuilder;

@RestController
@RequestMapping(value = "/entities" , produces = MediaType.APPLICATION_JSON_VALUE)
public class EntityController {
    public final ModelMapperConfig modelMapper;
    public final EntityService entityService;
    public final ClientService clientService;

    public EntityController(ModelMapperConfig modelMapper, EntityService entityService, ClientService clientService) {
        this.modelMapper = modelMapper;
        this.entityService = entityService;
        this.clientService = clientService;
    }

    @PostMapping("/rentalTime")
    public ResponseEntity<RentalTimeDTO> addRentalTimeToEntity(@RequestBody RentalTimeDTO rentalTimeDTO){
        RentalTime rentalTime = modelMapper.modelMapper().map(rentalTimeDTO, RentalTime.class);
        if(entityService.addRentalTime(rentalTimeDTO.getEntity_id(), rentalTime) == null){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
        }else{
            return ResponseEntity.status(HttpStatus.CREATED).body(rentalTimeDTO);
        }
    }
    @PostMapping("/reservations")
    public ResponseEntity<ReservationDTO> addReservationToEntity(@RequestBody ReservationDTO reservationDTO) throws MessagingException {
        Reservation reservation = modelMapper.modelMapper().map(reservationDTO, Reservation.class);
        if(entityService.addReservation(reservationDTO.getEntity_id(), reservation) == null){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
        }else{
            return ResponseEntity.status(HttpStatus.CREATED).body(reservationDTO);
        }
    }
    @PostMapping("/{entity_id}")
    public String subscribe(@PathVariable("entity_id") Integer entity_id, @RequestParam("user_id") Integer user_id){
        if(clientService.subscribeToEntity(user_id, entity_id))
            return "created";
        else
            return "not created";
    }
}
