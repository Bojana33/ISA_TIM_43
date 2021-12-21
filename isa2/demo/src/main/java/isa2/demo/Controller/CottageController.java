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

import java.util.ArrayList;
import java.util.List;

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
    public Cottage addCottage(@RequestBody CottageDTO cottageDTO){
        Cottage cottage = cottageMapper.mapDtoToCottage(cottageDTO);
        cottage = cottageService.addNewCottage(cottage);
        return cottage;
    }
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{cottage_id}")
    public void deleteCottage(@PathVariable("cottage_id") Integer id){
        cottageService.deleteCottage(id);
    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{cottage_id}")
    public CottageDTO getCottage(@PathVariable("cottage_id") Integer id){
        CottageDTO cottageDTO = new CottageDTO();
        Cottage cottage = cottageService.findById(id);
        cottageDTO = cottageMapper.mapCottageToDto(cottage);
        return cottageDTO;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("")
    public List<CottageDTO> getAllCottages() {
        List<Cottage> cottages = cottageService.findAllCottages();
        List<CottageDTO> cottageDTOS = new ArrayList<>();
        for(Cottage cottage:cottages){
            cottageDTOS.add(cottageMapper.mapCottageToDto(cottage));
        }
        return cottageDTOS;
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("")
    public Cottage updateCottage(@RequestBody CottageDTO cottageDTO){
        Cottage cottage = cottageMapper.mapDtoToCottage(cottageDTO);
        cottage = cottageService.updateCottage(cottage);
        return cottage;
    }
}
