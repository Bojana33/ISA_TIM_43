package isa2.demo.Controller;

import isa2.demo.Model.User;
import isa2.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.print.attribute.standard.Media;
import java.nio.file.AccessDeniedException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class BasicController {

    @Autowired
    private UserService userService;

    @GetMapping(path = "/user/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> findAllUsers() throws AccessDeniedException {
        return userService.findAll();
    }
    @GetMapping(path="/landingPage")
    public String landingUnregistredUser() {
        return "success";
    }

    @GetMapping("/whoami")
    @PreAuthorize("hasAnyRole('USER', 'BOATOWNER', 'COTTAGEOWNER', 'ADMIN', 'CLIENT', 'INSTRUCTOR')")
    public User user(Principal user) {
        return this.userService.findByEmail(user.getName());
    }


    @GetMapping("/get_user/{id}")
    @PreAuthorize("hasAnyRole('USER', 'BOATOWNER', 'COTTAGEOWNER', 'ADMIN', 'CLIENT', 'INSTRUCTOR')")
    public User findUser(@PathVariable Integer id){
        return this.userService.findById(id);
    }
}
