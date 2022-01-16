package isa2.demo.Controller;

import isa2.demo.DTO.CottageDTO;
import isa2.demo.DTO.FreeEntityDTO;
import isa2.demo.DTO.Mappers.CottageMapper;
import isa2.demo.Exception.InvalidInputException;
import isa2.demo.Model.Address;
import isa2.demo.Model.Adventure;
import isa2.demo.Model.Cottage;

import isa2.demo.Model.Reservation;
import isa2.demo.Repository.OwnerRepository;
import isa2.demo.Service.CottageService;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
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
    public ResponseEntity<CottageDTO> addCottage(@RequestBody CottageDTO cottageDTO){
        Cottage cottage = cottageMapper.mapDtoToCottage(cottageDTO);
        cottage.setOwner(ownerRepository.findById(Integer.parseInt(cottageDTO.getCottageOwnerId())).get());
        cottage = cottageService.addNewCottage(cottage);
        cottageDTO = cottageMapper.mapCottageToDto(cottage);
        return ResponseEntity.status(HttpStatus.OK).body(cottageDTO);
    }
    @DeleteMapping("/{cottage_id}")
    public ResponseEntity<CottageDTO> deleteCottage(@PathVariable("cottage_id") Integer id){
        CottageDTO cottageDTO = new CottageDTO();
        try{
            Cottage cottage = cottageService.deleteCottage(id);
            cottage.setPhotos(null);
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
    public List<CottageDTO> getCottages(@RequestParam(defaultValue = "") String cottageName) {
        List<Cottage> cottages = new ArrayList<>();
        cottages = cottageService.findCottagesByName(cottageName);
//        if(name.equals("")){
//            cottages = cottageService.findCottagesByName(name);
//        }else{
//            cottages = cottageService.findCottagesByName();
//        }

        List<CottageDTO> cottageDTOS = new ArrayList<>();
        for(Cottage cottage:cottages){
            cottageDTOS.add(cottageMapper.mapCottageToDto(cottage));
        }
        return cottageDTOS;
    }

    @PutMapping("/{cottage_id}")
    public ResponseEntity<CottageDTO> updateCottage(@RequestBody CottageDTO cottageDTO){
        ResponseEntity responseEntity = null;
        try{
            Cottage cottage = cottageMapper.mapDtoToCottage(cottageDTO);
            cottage = cottageService.updateCottage(cottage);
            cottageDTO = cottageMapper.mapCottageToDto(cottage);
            responseEntity = ResponseEntity.ok(cottageDTO);

        } catch (UnsupportedOperationException e){
            responseEntity = ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
        }

        return responseEntity;
    }

    @GetMapping(value =  "/get_all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CottageDTO>> getAll(){
        List<Cottage> cottages = this.cottageService.findAll();
        List<CottageDTO> cottageDTOS = new ArrayList<>();
        for (Cottage cottage : cottages)
            cottageDTOS.add(this.cottageMapper.mapCottageToDto(cottage));
        return new ResponseEntity<>(cottageDTOS, HttpStatus.OK);
    }

    @RequestMapping(value = "/findFree", method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<CottageDTO>> getFreeCottages(@RequestBody FreeEntityDTO request){
        try {
            Collection<Cottage> cottages = this.cottageService.findFreeCottages(request);
         ///   List<Cottage> cottagesSorted = this.cottageService.sortCottages(cottages, 0, true);
            Collection<CottageDTO> cottageDTOS = new ArrayList<>();
            for (Cottage cottage : cottages)
                cottageDTOS.add(this.cottageMapper.mapCottageToDto(cottage));
            return new ResponseEntity<>(cottageDTOS, HttpStatus.OK);
        }
        catch (InvalidInputException e){
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @RequestMapping(value = "/sorted/{criterion}/{asc}",  method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CottageDTO>> getSorted(@PathVariable("criterion")String criterion, @PathVariable("asc") Boolean asc,@RequestBody Collection<CottageDTO> cottages){
        List<CottageDTO> cottagesSorted = this.cottageService.sortCottages(cottages, criterion, asc);
        return new ResponseEntity<>(cottagesSorted, HttpStatus.OK);
    }
}
