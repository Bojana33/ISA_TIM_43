package isa2.demo.Controller;

import isa2.demo.Config.ModelMapperConfig;
import isa2.demo.DTO.CottageDTO;
import isa2.demo.Model.Address;
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

    public final ModelMapperConfig modelMapper;
    public final CottageService cottageService;

    public CottageController(CottageService cottageService, ModelMapperConfig modelMapper) {
        this.cottageService = cottageService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public String helloFromCottages(){
        return "Hello";
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public Cottage addNewCottage(@RequestBody CottageDTO cottageDTO){
        Cottage cottage1 = modelMapper.modelMapper().map(cottageDTO, Cottage.class);
//        Address address = modelMapper.modelMapper().map(cottageDTO.getAddressDTO(), Address.class);
//        cottage1.setAddress(address);
        cottage1 = cottageService.addNewCottage(cottage1);
        //Cottage cottage = cottageService.addNewCottage(cottageDTO);
        return cottage1;
    }
}
