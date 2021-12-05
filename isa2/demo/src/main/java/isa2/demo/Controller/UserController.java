package isa2.demo.Controller;

import isa2.demo.Exception.EmailNotExistsException;
import isa2.demo.Model.User;
import isa2.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import java.security.Principal;

@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(path = "/update", method = RequestMethod.POST)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<? extends Object> updateUser(Principal user, @RequestBody User userUpdate) {
        try {
            User updateUser = userService.updateUser(userUpdate, user.getName());
            return new ResponseEntity<User>(updateUser, HttpStatus.CREATED);
        } catch (EmailNotExistsException e) {
            return new ResponseEntity<EmailNotExistsException>(new EmailNotExistsException("User not exists"), HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(path = "/userInfo", method = RequestMethod.GET)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<? extends Object> getUserInfo(Principal user) {
        try {
            User user1 = userService.findByUsername(user.getName());
            return new ResponseEntity<User>(user1, HttpStatus.CREATED);
        } catch (EmailNotExistsException e) {
            return new ResponseEntity<EmailNotExistsException>(new EmailNotExistsException("User not exists"), HttpStatus.FORBIDDEN);
        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<UsernameNotFoundException>(new UsernameNotFoundException("User not exists"), HttpStatus.FORBIDDEN);
        }
    }
}
