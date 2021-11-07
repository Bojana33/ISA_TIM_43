package isa2.demo.Controller;

import isa2.demo.Config.ModelMapperConfig;
import isa2.demo.DTO.CottageDTO;
import isa2.demo.DTO.Mappers.CottageMapper;
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

    public final CottageService cottageService;
    public final CottageMapper cottageMapper;

    public CottageController(CottageService cottageService, CottageMapper cottageMapper) {
        this.cottageService = cottageService;
        this.cottageMapper = cottageMapper;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public Cottage addNewCottage(@RequestBody CottageDTO cottageDTO){
        Cottage cottage = cottageMapper.mapDtoToCottage(cottageDTO);
        cottage = cottageService.addNewCottage(cottage);
        return cottage;
    }
}
