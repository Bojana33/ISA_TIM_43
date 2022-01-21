package isa2.demo.Controller;

import isa2.demo.Config.ModelMapperConfig;
import isa2.demo.DTO.AdventureDTO;
import isa2.demo.DTO.BoatDTO;
import isa2.demo.DTO.FreeEntityDTO;
import isa2.demo.DTO.Mappers.AdventureMapper;
import isa2.demo.DTO.UserDTO;
import isa2.demo.Exception.InvalidInputException;
import isa2.demo.Model.Adventure;
import isa2.demo.Model.Boat;
import isa2.demo.Model.Entity;
import isa2.demo.Model.Owner;
import isa2.demo.Service.AdventureService;
import isa2.demo.Service.EntityService;
import isa2.demo.Service.OwnerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping(value = "/adventures" , produces = MediaType.APPLICATION_JSON_VALUE)
public class AdventureController {

    public final AdventureService adventureService;
    public final AdventureMapper adventureMapper;
    private final ModelMapperConfig modelMapper;
    private final EntityService entityService;
    private final OwnerService ownerService;

    public AdventureController(AdventureService adventureService, AdventureMapper adventureMapper, ModelMapperConfig modelMapper, EntityService entityService,
                               OwnerService ownerService){
        this.adventureService = adventureService;
        this.adventureMapper = adventureMapper;
        this.modelMapper = modelMapper;
        this.entityService = entityService;
        this.ownerService = ownerService;
    }

    @GetMapping(value = "/get_all_adventures", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AdventureDTO>> getAllAdventures(){
        List<Adventure> adventures = this.adventureService.findAll();
        List<AdventureDTO> adventureDTOS = new ArrayList<>();
        for(Adventure adventure : adventures)
            adventureDTOS.add(adventureMapper.mapAdventureToDTO(adventure));
        return new ResponseEntity<>(adventureDTOS, HttpStatus.OK);
    }

    @GetMapping("/get_adventure/{id}")
    public ResponseEntity<AdventureDTO> getAdventure(@PathVariable Integer id){
         AdventureDTO adventureDTO = adventureMapper.mapAdventureToDTO(this.adventureService.findOne(id).orElse(null));
        return new ResponseEntity<>(adventureDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/add_adventure",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity<Adventure> addAdventure(@RequestBody AdventureDTO adventureDTO){
        Adventure adventure = adventureMapper.mapDtoToAdventure(adventureDTO);
        // TODO: Add additional services
        return new ResponseEntity<>(this.adventureService.save(adventure), HttpStatus.CREATED);
    }

    @PostMapping(value = "/save_adventure_image/{id}",consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity<Adventure> saveImage(@PathVariable Integer id,@RequestParam("imageUrl") MultipartFile imageUrl){
        Path path = Paths.get("client/src/assets/images");
        Adventure adventure = this.adventureService.findOne(id).orElse(null);
        try{
            InputStream inputStream = imageUrl.getInputStream();
            Files.copy(inputStream, path.resolve(imageUrl.getOriginalFilename()),
                    StandardCopyOption.REPLACE_EXISTING);
            adventure.getPhotos().add("./../../assets/images/"+ imageUrl.getOriginalFilename());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(this.adventureService.update(adventure), HttpStatus.OK);
    }

    @PostMapping("/update_adventure/{id}")
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity<Adventure> updateAdventure(@RequestBody AdventureDTO adventureDTO, @PathVariable Integer id){
        Adventure adventure = adventureMapper.mapDtoToAdventure(adventureDTO);
        return new ResponseEntity<>(this.adventureService.update(adventure), HttpStatus.OK);
    }

    @DeleteMapping("/delete_adventure/{id}")
    @PreAuthorize("hasAnyRole('INSTRUCTOR','ADMIN')")
    public void deleteAdventure(@PathVariable Integer id){
        this.adventureService.delete(id);
    }

    @RequestMapping(value = "/findFree", method= RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AdventureDTO>> getFreeCottages(@RequestBody FreeEntityDTO request){
        try {
            Collection<Adventure> adventures = this.adventureService.findFreeAdventures(request);
            Collection<AdventureDTO> adventureDTOS = new ArrayList<>();
            for (Adventure adventure : adventures)
                adventureDTOS.add(this.adventureMapper.mapAdventureToDTO(adventure));
            return new ResponseEntity<>(adventureDTOS, HttpStatus.OK);
        }
        catch (InvalidInputException e){
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    //@GetMapping(value="/getInstructor/{entityId}")
    //public ResponseEntity<UserDTO> getInstructorOfAdventure(@PathVariable Integer entityId){
      //  Entity entity = entityService.findById(entityId);
      //  Owner owner = ownerService.findByEntity(entity);
       // return new ResponseEntity<UserDTO>(modelMapper.modelMapper().map(owner,UserDTO.class), HttpStatus.OK);
    //}
}
