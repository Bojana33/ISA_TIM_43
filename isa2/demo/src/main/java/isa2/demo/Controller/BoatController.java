package isa2.demo.Controller;

import isa2.demo.DTO.BoatDTO;
import isa2.demo.DTO.CottageDTO;
import isa2.demo.DTO.FreeEntityDTO;
import isa2.demo.DTO.Mappers.BoatMapper;
import isa2.demo.Exception.InvalidInputException;
import isa2.demo.Model.Adventure;
import isa2.demo.Model.Boat;
import isa2.demo.Model.Cottage;
import isa2.demo.Service.BoatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping(value = "/boats", produces = MediaType.APPLICATION_JSON_VALUE)
public class BoatController {

    @Autowired
    private BoatService boatService;

    public final BoatMapper boatMapper;

    public BoatController(BoatMapper boatMapper) {
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

    @RequestMapping(value = "/findFree", method= RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<BoatDTO>> getFreeCottages(@RequestBody FreeEntityDTO request){
        try {
            Collection<Boat> boats = this.boatService.findFreeBoats(request);
            Collection<BoatDTO> boatDTOS = new ArrayList<>();
            for (Boat boat : boats)
                boatDTOS.add(this.boatMapper.mapBoatToDTO(boat));
            return new ResponseEntity<>(boatDTOS, HttpStatus.OK);
        }
        catch (InvalidInputException e){
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
