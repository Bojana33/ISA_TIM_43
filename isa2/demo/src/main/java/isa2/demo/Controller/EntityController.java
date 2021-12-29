package isa2.demo.Controller;

import isa2.demo.Config.ModelMapperConfig;
import isa2.demo.DTO.CottageDTO;
import isa2.demo.DTO.RentalTimeDTO;
import isa2.demo.DTO.ReservationDTO;
import isa2.demo.Model.Cottage;
import isa2.demo.Model.Entity;
import isa2.demo.Model.RentalTime;
import isa2.demo.Model.Reservation;
import isa2.demo.Service.ClientService;
import isa2.demo.Service.EntityService;
import isa2.demo.Service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value = "/entities" , produces = MediaType.APPLICATION_JSON_VALUE)
public class EntityController {
    private final ModelMapperConfig modelMapper;
    private final EntityService entityService;
    private final ClientService clientService;
    private final ReservationService reservationService;

    public EntityController(ModelMapperConfig modelMapper, EntityService entityService,
                            ClientService clientService, ReservationService reservationService) {
        this.modelMapper = modelMapper;
        this.entityService = entityService;
        this.clientService = clientService;
        this.reservationService = reservationService;
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
        if(entityService.addReservation(reservationDTO.getEntityId(), reservation) == null){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
        }else{
            return ResponseEntity.status(HttpStatus.CREATED).body(reservationDTO);
        }
    }
    @GetMapping("/reservations/{entityId}")
    public ResponseEntity<Collection<ReservationDTO>> getReservations(@PathVariable Integer entityId){
        Collection<Reservation> reservations = reservationService.findAllReservationsForEntity(entityId);
        Collection<ReservationDTO> reservationDTOS = new ArrayList<>();

        for(Reservation reservation:reservations){
            reservationDTOS.add(modelMapper.modelMapper().map(reservation,ReservationDTO.class));
        }
        return ResponseEntity.ok().body(reservationDTOS);

    }
    @PostMapping("/{entity_id}")
    public String subscribe(@PathVariable("entity_id") Integer entity_id, @RequestParam("user_id") Integer user_id){
        if(clientService.subscribeToEntity(user_id, entity_id))
            return "created";
        else
            return "not created";
    }
}
