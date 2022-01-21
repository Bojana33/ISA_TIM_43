package isa2.demo.Controller;

import isa2.demo.DTO.Mappers.UserMapper;
import isa2.demo.DTO.UserDTO;
import isa2.demo.Exception.EmailNotExistsException;
import isa2.demo.Model.User;
import isa2.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    @Autowired
    private UserService userService;

    public final UserMapper userMapper;

    public UserController(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @RequestMapping(path = "/update", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('USER', 'BOATOWNER', 'COTTAGEOWNER', 'ADMIN', 'CLIENT', 'INSTRUCTOR')")
    public ResponseEntity<? extends Object> updateUser(@RequestBody UserDTO userUpdateDTO, Principal user) {
        try {
            User userUpdate = userMapper.mapDtoToUser(userUpdateDTO);
            User updateUser = userService.updateUser(userUpdate, user.getName());
            return new ResponseEntity<User>(updateUser, HttpStatus.CREATED);
        } catch (EmailNotExistsException e) {
            return new ResponseEntity<EmailNotExistsException>(new EmailNotExistsException("User not exists"), HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(path = "/userInfo", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('USER', 'BOATOWNER', 'COTTAGEOWNER', 'ADMIN', 'CLIENT', 'INSTRUCTOR')")
    public UserDTO getUserInfo(Principal user) {
            User user1 = userService.findByUsername(user.getName());
            UserDTO userDTO = userMapper.mapUserToDto(user1);
            return userDTO;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete_user/{id}")
    public void deleteUser(@PathVariable Integer id){
        User user = this.userService.findById(id);
        this.userService.delete(user);
    }
}
