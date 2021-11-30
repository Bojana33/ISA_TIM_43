package isa2.demo.Controller;

import isa2.demo.DTO.JwtAuthenticationRequest;
import isa2.demo.Exception.ResourceConflictException;
import isa2.demo.Model.RegistrationRequest;
import isa2.demo.Model.User;
import isa2.demo.Model.UserRequest;
import isa2.demo.DTO.UserTokenState;
import isa2.demo.Service.RegistrationRequestService;
import isa2.demo.Service.ServiceImpl.CustomUserDetailsService;
import isa2.demo.Service.UserService;
import isa2.demo.Utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private RegistrationRequestService registrationRequestService;

    // Prvi endpoint koji pogadja korisnik kada se loguje.
    // Tada zna samo svoje korisnicko ime i lozinku i to prosledjuje na backend.
    @PostMapping("/login")
    public ResponseEntity<UserTokenState> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest,
                                                                    HttpServletResponse response) {

        //
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()));

        // Ubaci korisnika u trenutni security kontekst
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Kreiraj token za tog korisnika
        User user = (User) authentication.getPrincipal();
        String jwt = tokenUtils.generateToken(user.getUsername());
        int expiresIn = tokenUtils.getExpiredIn();

        // Vrati token kao odgovor na uspesnu autentifikaciju
        return ResponseEntity.ok(new UserTokenState(jwt, (long) expiresIn));
    }

    // Endpoint za registraciju novog korisnika
    @PostMapping("/signup")
    public ResponseEntity<? extends Object> addUser(@RequestBody RegistrationRequest registrationRequest, UriComponentsBuilder ucBuilder) {

        User existUser = this.userService.findByEmail(registrationRequest.getEmail());
        RegistrationRequest existRequest = this.registrationRequestService.findByEmail(registrationRequest.getEmail());
        if (existUser != null || existRequest != null) {
            throw new ResourceConflictException(registrationRequest.getId(), "Email already exists");
        }
        //try{
            RegistrationRequest request = this.registrationRequestService.save(registrationRequest);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/api/user/{userId}").buildAndExpand(request.getId()).toUri());
            return new ResponseEntity<>(request, HttpStatus.CREATED);
//        } catch (MessagingException me) {
//            System.out.println("Message exception");
//            return new ResponseEntity<MessagingException>(new MessagingException(), HttpStatus.FORBIDDEN);
//        }
//        catch (EmailAlreadyInUseException e) {
//            System.out.println("Email already in use");
//            return new ResponseEntity<EmailAlreadyInUseException>(new EmailAlreadyInUseException("Email already in use"), HttpStatus.FORBIDDEN);
//        }

    }

    // U slucaju isteka vazenja JWT tokena, endpoint koji se poziva da se token osvezi
    @GetMapping(value = "/refresh")
    public ResponseEntity<UserTokenState> refreshAuthenticationToken(HttpServletRequest request) {

        String token = tokenUtils.getToken(request);
        String username = this.tokenUtils.getUsernameFromToken(token);
        User user = (User) this.userDetailsService.loadUserByUsername(username);

        if (this.tokenUtils.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
            String refreshedToken = tokenUtils.refreshToken(token);
            int expiresIn = tokenUtils.getExpiredIn();

            return ResponseEntity.ok(new UserTokenState(refreshedToken, (long) expiresIn));
        } else {
            UserTokenState userTokenState = new UserTokenState();
            return ResponseEntity.badRequest().body(userTokenState);
        }
    }

    @RequestMapping(value = "/change-password", method = RequestMethod.POST)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> changePassword(@RequestBody PasswordChanger passwordChanger) {
        userDetailsService.changePassword(passwordChanger.oldPassword, passwordChanger.newPassword);

        Map<String, String> result = new HashMap<>();
        result.put("result", "success");
        return ResponseEntity.accepted().body(result);
    }

    static class PasswordChanger {
        public String oldPassword;
        public String newPassword;
    }
}
