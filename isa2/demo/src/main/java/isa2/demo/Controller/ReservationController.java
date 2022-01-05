package isa2.demo.Controller;

import isa2.demo.Config.ModelMapperConfig;
import isa2.demo.DTO.ReservationDTO;
import isa2.demo.Model.Owner;
import isa2.demo.Model.Reservation;
import isa2.demo.Service.ServiceImpl.OwnerServiceImpl;
import isa2.demo.Service.ServiceImpl.ReservationServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;

@RestController
@RequestMapping(value = "/reservations", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReservationController {

    private final ReservationServiceImpl reservationService;
    private final OwnerServiceImpl ownerService;
    private final ModelMapperConfig modelMapper;

    public ReservationController(ReservationServiceImpl reservationService, OwnerServiceImpl ownerService, ModelMapperConfig modelMapper){
        this.reservationService = reservationService;
        this.ownerService = ownerService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/get_instructor_reservations/{instructorId}")
    public ResponseEntity<Collection<ReservationDTO>> getReservationsForInstructor(@PathVariable Integer instructorId){
        Owner instructor = this.ownerService.findById(instructorId);
        Collection<ReservationDTO> reservationDTOS = new ArrayList<>();
        Collection<Reservation> reservations = this.reservationService.findAllReservationsForInstructor(instructor);
        for(Reservation reservation:reservations){
            reservationDTOS.add(modelMapper.modelMapper().map(reservation, ReservationDTO.class));
        }
        return new ResponseEntity<>(reservationDTOS, HttpStatus.OK);
    }
}
