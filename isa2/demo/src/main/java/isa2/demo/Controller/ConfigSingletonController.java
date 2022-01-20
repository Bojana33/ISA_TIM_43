package isa2.demo.Controller;

import isa2.demo.Model.ConfigSingleton;
import isa2.demo.Service.ConfigSingletonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/config")
public class ConfigSingletonController {

    private ConfigSingletonService configSingletonService;

    public ConfigSingletonController(ConfigSingletonService configSingletonService){
        this.configSingletonService = configSingletonService;
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ConfigSingleton> update(@RequestBody ConfigSingleton configSingleton){
        try {
            return new ResponseEntity<>(this.configSingletonService.update(configSingleton), HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(configSingleton,HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/get_config")
    @PreAuthorize("hasRole('ADMIN')")
    public ConfigSingleton get(){
        return this.configSingletonService.getConfig(1);
    }
}
