package isa2.demo.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class BasicController {
    @GetMapping(path="/landingPage")
    public String landingUnregistredUser() {
        return "success";
    }
}