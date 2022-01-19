package isa2.demo.Controller;

import isa2.demo.Config.ModelMapperConfig;
import isa2.demo.DTO.*;
import isa2.demo.DTO.Mappers.AdditionalServiceMapper;
import isa2.demo.Exception.InvalidReservationException;
import isa2.demo.Model.*;
import isa2.demo.DTO.PeriodDTO;
import isa2.demo.DTO.RentalTimeDTO;
import isa2.demo.DTO.ReservationDTO;
import isa2.demo.Model.*;
import isa2.demo.Service.ClientService;
import isa2.demo.Service.EntityService;
import isa2.demo.Service.ReservationService;
import isa2.demo.Utils.FileUploadUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.*;
import java.security.Principal;
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
    private final AdditionalServiceMapper additionalServiceMapper;

    public EntityController(ModelMapperConfig modelMapper, EntityService entityService,
                            ClientService clientService, ReservationService reservationService, AdditionalServiceMapper additionalServiceMapper) {
        this.modelMapper = modelMapper;
        this.entityService = entityService;
        this.clientService = clientService;
        this.reservationService = reservationService;
        this.additionalServiceMapper = additionalServiceMapper;
    }

    @PostMapping("/rentalTime")
    public ResponseEntity<RentalTimeDTO> addRentalTimeToEntity(@RequestBody RentalTimeDTO rentalTimeDTO) throws MessagingException {
        RentalTime rentalTime = modelMapper.modelMapper().map(rentalTimeDTO, RentalTime.class);
        ResponseEntity responseEntity = null;

        try{
            entityService.addRentalTime(rentalTimeDTO.getEntity_id(), rentalTime);
            responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(rentalTimeDTO);
        }
        catch (MessagingException e){
            responseEntity = ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
        }
        return responseEntity;
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
    public ResponseEntity<Collection<ReservationDTO>> getReservations(@PathVariable Integer entityId, @RequestBody Optional<PeriodDTO> periodDTO){
        Optional<Period> periodOptional = Optional.empty();
        if(periodDTO.isPresent()){
            periodOptional = Optional.of(modelMapper.modelMapper().map(periodDTO.get(),Period.class));
        }
        Collection<Reservation> reservations = reservationService.findAllReservationsForEntity(entityId,periodOptional);
        Collection<ReservationDTO> reservationDTOS = new ArrayList<>();

        for(Reservation reservation:reservations){
            reservationDTOS.add(modelMapper.modelMapper().map(reservation,ReservationDTO.class));
        }
        return ResponseEntity.ok().body(reservationDTOS);
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping("/subscribe/{entity_id}")
    public String subscribe(@PathVariable Integer entity_id, Principal user){
        if(clientService.subscribeToEntity(user.getName(), entity_id))
            return "created";
        else
            return "not created";
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PutMapping("/unsubscribe/{entity_id}")
    public ResponseEntity<String> unsubscribe(@PathVariable Integer entity_id, Principal user){
        try {
            clientService.unsubscribe(user.getName(), entity_id);
            return new ResponseEntity("unsubscribed", HttpStatus.OK);
        }catch(Exception e) {
            return new ResponseEntity("exception", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping(value = "/save_image/{id}",consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
//    @PreAuthorize("hasRole('COTTAGEOWNER')")
    public void saveImage(@PathVariable Integer id,@RequestParam("imageUrl") MultipartFile imageUrl) throws IOException {
        String fileName = StringUtils.cleanPath(imageUrl.getOriginalFilename());

        String uploadDir = "../../client/src/assets/images";
        entityService.uploadEntityPhoto(id,"./../../assets/images/" + fileName);
        FileUploadUtil.saveFile(uploadDir, fileName, imageUrl);

    }

    @PreAuthorize("hasRole('CLIENT')")
    @RequestMapping(value = "/reserve", method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReservationDTO> reserve(@RequestBody ReservationDTO request){
        try{
            Reservation reservation = reservationService.reserveEntity(request);
            return new ResponseEntity<>(modelMapper.modelMapper().map(reservation, ReservationDTO.class), HttpStatus.OK);
        }
        catch(InvalidReservationException e) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping(value = "/isSubscribed/{entity_id}")
    public boolean isSubscribed(@PathVariable Integer entity_id, Principal user){
        return clientService.isSubscribed(user.getName(), entity_id);
    }

    @PreAuthorize("hasRole('CLIENT')")
    @RequestMapping(value = "/fastReservation", method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReservationDTO> fastReserve(@RequestBody ReservationDTO request){
        try{
            Reservation reservation = reservationService.fastReservation(request);
            return new ResponseEntity<>(modelMapper.modelMapper().map(reservation, ReservationDTO.class), HttpStatus.OK);
        }
        catch(InvalidReservationException e) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping(value="/getEntityById/{entityId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityDTO> getEntity(@PathVariable Integer entityId){
        Entity entity = entityService.findById(entityId);
        return new ResponseEntity<>(modelMapper.modelMapper().map(entity, EntityDTO.class), HttpStatus.OK);
    }
}
