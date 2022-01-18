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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

    @GetMapping("/get_owner_reservations/{ownerId}")
    public ResponseEntity<Collection<ReservationDTO>> getReservationsForOwner(@PathVariable Integer ownerId){
        Owner owner = this.ownerService.findById(ownerId);
        Collection<ReservationDTO> reservationDTOS = new ArrayList<>();
        Collection<Reservation> reservations = this.reservationService.findAllReservationsForOwner(owner);
        for(Reservation reservation:reservations){
            reservationDTOS.add(modelMapper.modelMapper().map(reservation, ReservationDTO.class));
        }
        return new ResponseEntity<>(reservationDTOS, HttpStatus.OK);
    }

    @GetMapping("/getAllFutureReservationsOnSale")
    public ResponseEntity<Collection<ReservationDTO>> getFutureReservationsOnSale(){
        Collection<Reservation> reservations = this.reservationService.findAllFutureReservationsOnSale();
        Collection<ReservationDTO> reservationDTOS = new ArrayList<>();
        for (Reservation reservation : reservations)
            reservationDTOS.add(modelMapper.modelMapper().map(reservation, ReservationDTO.class));
        return new ResponseEntity<>(reservationDTOS, HttpStatus.OK);
    }

    @GetMapping("/getAllUserReservations/{clientId}")
    public ResponseEntity<Collection<ReservationDTO>> getListOfReservations(@PathVariable Integer clientId){
        Collection<Reservation> reservations = this.reservationService.findAllReservationsForClient(clientId);
        Collection<ReservationDTO> reservationDTOS = new ArrayList<>();
        for (Reservation reservation : reservations)
            reservationDTOS.add(modelMapper.modelMapper().map(reservation, ReservationDTO.class));
        return new ResponseEntity<>(reservationDTOS, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PutMapping("/cancelReservation/{reservationId}")
    public ResponseEntity<String> cancelReservation(@PathVariable Integer reservationId, Principal user){
        try{
            this.reservationService.cancelReservation(user.getName(), reservationId);
            return new ResponseEntity("Successfully canceled", HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity("Something went wrong", HttpStatus.FORBIDDEN);
        }
    }
}
