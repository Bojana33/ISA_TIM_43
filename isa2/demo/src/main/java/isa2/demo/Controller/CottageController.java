package isa2.demo.Controller;

import isa2.demo.Config.ModelMapperConfig;
import isa2.demo.DTO.CottageDTO;
import isa2.demo.DTO.Mappers.CottageMapper;
import isa2.demo.Model.Address;
import isa2.demo.Model.Cottage;

import isa2.demo.Repository.OwnerRepository;
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
    public final OwnerRepository ownerRepository;

    public CottageController(CottageService cottageService, CottageMapper cottageMapper, OwnerRepository ownerRepository) {
        this.cottageService = cottageService;
        this.cottageMapper = cottageMapper;
        this.ownerRepository = ownerRepository;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public Cottage addCottage(@RequestBody CottageDTO cottageDTO){
        Cottage cottage = cottageMapper.mapDtoToCottage(cottageDTO);
        cottage.setOwner(ownerRepository.findById(Integer.parseInt(cottageDTO.getCottageOwnerId())).get());
        cottage = cottageService.addNewCottage(cottage);
            return cottage;
    }
    @DeleteMapping("/{cottage_id}")
    public ResponseEntity<CottageDTO> deleteCottage(@PathVariable("cottage_id") Integer id){
        CottageDTO cottageDTO = new CottageDTO();
        try{
            Cottage cottage = cottageService.deleteCottage(id);
            //TODO: popravi ovaj exception u mapperu
            cottageDTO = cottageMapper.mapCottageToDto(cottage);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(cottageDTO);
        }
       return ResponseEntity.status(HttpStatus.OK).body(cottageDTO);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{cottage_id}")
    public CottageDTO getCottage(@PathVariable("cottage_id") Integer id){
        CottageDTO cottageDTO = new CottageDTO();
        Cottage cottage = cottageService.findById(id).orElse(null);
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
