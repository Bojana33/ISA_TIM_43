package isa2.demo.Controller;

import isa2.demo.Config.ModelMapperConfig;
import isa2.demo.DTO.AdventureDTO;
import isa2.demo.DTO.RentalTimeDTO;
import isa2.demo.DTO.ReservationDTO;
import isa2.demo.Model.Adventure;
import isa2.demo.Model.RentalTime;
import isa2.demo.Model.Reservation;
import isa2.demo.Service.EntityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/entities" , produces = MediaType.APPLICATION_JSON_VALUE)
public class EntityController {
    public final ModelMapperConfig modelMapper;
    public final EntityService entityService;

    public EntityController(ModelMapperConfig modelMapper, EntityService entityService) {
        this.modelMapper = modelMapper;
        this.entityService = entityService;
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
    public ResponseEntity<ReservationDTO> addReservationToEntity(@RequestBody ReservationDTO reservationDTO){
        Reservation reservation = modelMapper.modelMapper().map(reservationDTO, Reservation.class);
        if(entityService.addReservation(reservationDTO.getEntity_id(), reservation) == null){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
        }else{
            return ResponseEntity.status(HttpStatus.CREATED).body(reservationDTO);
        }
    }
}
