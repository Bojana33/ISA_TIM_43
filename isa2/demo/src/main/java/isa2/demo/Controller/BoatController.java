package isa2.demo.Controller;

import isa2.demo.Model.Boat;
import isa2.demo.Service.BoatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/boats", produces = MediaType.APPLICATION_JSON_VALUE)
public class BoatController {

    @Autowired
    private BoatService boatService;

    @GetMapping("/get_all")
    public ResponseEntity<List<Boat>> getAll(){
        return new ResponseEntity<>(this.boatService.findAll(), HttpStatus.OK);
    }
}
