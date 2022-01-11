package isa2.demo.Controller;

import isa2.demo.DTO.BoatDTO;
import isa2.demo.DTO.CottageDTO;
import isa2.demo.DTO.Mappers.BoatMapper;
import isa2.demo.Model.Adventure;
import isa2.demo.Model.Boat;
import isa2.demo.Model.Cottage;
import isa2.demo.Repository.OwnerRepository;
import isa2.demo.Service.BoatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/boats", produces = MediaType.APPLICATION_JSON_VALUE)
public class BoatController {

    public final BoatService boatService;
    public final BoatMapper boatMapper;
    public final OwnerRepository ownerRepository;

    public BoatController(BoatMapper boatMapper, OwnerRepository ownerRepository, BoatService boatService)
    {
        this.boatService = boatService;
        this.ownerRepository = ownerRepository;
        this.boatMapper = boatMapper;
    }

    @GetMapping("/get_all")
    public ResponseEntity<List<BoatDTO>> getAll(){
        List<Boat> boats = this.boatService.findAll();
        List<BoatDTO> boatDTOS = new ArrayList<>();
        for(Boat boat : boats)
            boatDTOS.add(boatMapper.mapBoatToDTO(boat));
        return new ResponseEntity<>(boatDTOS, HttpStatus.OK);
    }

    @GetMapping("/get_boat/{id}")
    public ResponseEntity<Boat> getBoat(@PathVariable Integer id){
        return new ResponseEntity<>(this.boatService.findOne(id), HttpStatus.OK);
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public Boat addBoat(@RequestBody BoatDTO boatDTO){
        Boat boat = boatMapper.mapDtoToBoat(boatDTO);
        boat.setOwner(ownerRepository.findById(boatDTO.getBoatOwnerId()).get());
        boat = boatService.addNewBoat(boat);
        return boat;
    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{boat_id}")
    public BoatDTO getBoatById(@PathVariable("boat_id") Integer id){
        BoatDTO boatDTO = new BoatDTO();
        Boat boat = boatService.findOne(id);
        boatDTO = boatMapper.mapBoatToDTO(boat);
        return boatDTO;
    }
    @DeleteMapping("/{boat_id}")
    public ResponseEntity<BoatDTO> deleteBoat(@PathVariable("boat_id") Integer id){
        BoatDTO boatDTO = new BoatDTO();
        try{
            Boat boat = boatService.deleteBoat(id);
            boat.setPhotos(null);
            boatDTO = boatMapper.mapBoatToDTO(boat);
        }catch (UnsupportedOperationException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(boatDTO);
        }
        return ResponseEntity.status(HttpStatus.OK).body(boatDTO);
    }
    @PutMapping("/{boat_id}")
    public ResponseEntity<BoatDTO> updateCottage(@RequestBody BoatDTO boatDTO){
        ResponseEntity responseEntity = null;
        try{
            Boat boat = boatMapper.mapDtoToBoat(boatDTO);
            boat = boatService.updateBoat(boat);
            boatDTO = boatMapper.mapBoatToDTO(boat);
            responseEntity = ResponseEntity.ok(boatDTO);

        } catch (UnsupportedOperationException e){
            responseEntity = ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
        }

        return responseEntity;
    }
}
