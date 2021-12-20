package isa2.demo.Controller;

import isa2.demo.DTO.AdventureDTO;
import isa2.demo.DTO.Mappers.AdventureMapper;
import isa2.demo.Model.Adventure;
import isa2.demo.Service.AdventureService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@RestController
@RequestMapping(value = "/adventures" , produces = MediaType.APPLICATION_JSON_VALUE)
public class AdventureController {

    public final AdventureService adventureService;
    public final AdventureMapper adventureMapper;

    public AdventureController(AdventureService adventureService, AdventureMapper adventureMapper){
        this.adventureService = adventureService;
        this.adventureMapper = adventureMapper;
    }

    @GetMapping(value = "/get_all_adventures", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Adventure>> getAllAdventures(){
        return new ResponseEntity<>(this.adventureService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/get_adventure/{id}")
    public ResponseEntity<Adventure> getAdventure(@PathVariable Integer id){
        // TODO: Show photos of this adventure
        return new ResponseEntity<>(this.adventureService.findOne(id), HttpStatus.OK);
    }

    @PostMapping(value = "/add_adventure",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Adventure> addAdventure(@RequestBody AdventureDTO adventureDTO){
        Adventure adventure = adventureMapper.mapDtoToAdventure(adventureDTO);
        // TODO: Add additional services    
        return new ResponseEntity<>(this.adventureService.save(adventure), HttpStatus.CREATED);
    }

    @PostMapping(value = "/save_adventure_image/{id}",consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Adventure> saveImage(@PathVariable Integer id,@RequestParam("imageUrl") MultipartFile imageUrl){
        Path path = Paths.get("E:\\Internet_Softverske_Arhitekture\\projekat2\\Git\\ISA_TIM_43\\client\\src\\assets\\images");
        Adventure adventure = this.adventureService.findOne(id);
        try{
            InputStream inputStream = imageUrl.getInputStream();
            Files.copy(inputStream, path.resolve(imageUrl.getOriginalFilename()),
                    StandardCopyOption.REPLACE_EXISTING);
            adventure.getPhotos().add("./../../assets/images/"+ imageUrl.getOriginalFilename().toLowerCase());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(this.adventureService.update(id,adventure), HttpStatus.OK);
    }

    @PostMapping("/update_adventure/{id}")
    public ResponseEntity<Adventure> updateAdventure(@RequestBody AdventureDTO adventureDTO, @PathVariable Integer id){
        Adventure adventure = adventureMapper.mapDtoToAdventure(adventureDTO);
        return new ResponseEntity<>(this.adventureService.update(id,adventure), HttpStatus.OK);
    }

    @GetMapping("/delete_adventure/{id}")
    public void deleteAdventure(@PathVariable Integer id){
        this.adventureService.delete(id);
    }
}
