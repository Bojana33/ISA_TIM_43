package isa2.demo.Controller;

import isa2.demo.DTO.CottageDTO;
import isa2.demo.Model.Cottage;

import isa2.demo.Service.CottageService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/cottages", produces = MediaType.APPLICATION_JSON_VALUE)
public class CottageController {

    public final CottageService cottageService;

    public CottageController(CottageService cottageService) {
        this.cottageService = cottageService;
    }

    @GetMapping
    public String helloFromCottages(){
        return "Hello";
    }
    @PostMapping("")
    public ResponseEntity<Cottage> addNewCottage(@RequestBody CottageDTO cottageDTO){
        Cottage cottage = cottageService.addNewCottage(cottageDTO);
        return new ResponseEntity<>(cottage,HttpStatus.CREATED);
    }
}
