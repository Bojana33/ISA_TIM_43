package isa2.demo.Controller;

import isa2.demo.DTO.BoatDTO;
import isa2.demo.DTO.Mappers.BoatMapper;
import isa2.demo.Model.Boat;
import isa2.demo.Service.BoatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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
}
