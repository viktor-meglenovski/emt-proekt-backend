package threed.manager.backend.security.xport;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import threed.manager.backend.security.domain.AppUser;
import threed.manager.backend.security.domain.Test;
import threed.manager.backend.security.domain.exceptions.InvalidUsernameOrPasswordException;
import threed.manager.backend.security.domain.exceptions.PasswordsDoNotMatchException;
import threed.manager.backend.security.domain.exceptions.UsernameAlreadyExistsException;
import threed.manager.backend.security.service.AuthenticationService;
import threed.manager.backend.security.service.UserService;
import threed.manager.backend.sharedkernel.security.JwtValidator;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
public class AuthenticationRestController {

    private final AuthenticationService authenticationService;
    private final UserService userService;


    @PostMapping("/login")
    public ResponseEntity createCustomer(@RequestParam String email, @RequestParam String password) {
        return new ResponseEntity<>(authenticationService.generateJWTToken(email, password), HttpStatus.OK);
    }
    @PostMapping("/register")
    public AppUser registerNewUser(@RequestParam String email,
                                   @RequestParam String name,
                                   @RequestParam String surname,
                                   @RequestParam String password,
                                   @RequestParam String repeatPassword,
                                   @RequestParam String role)throws UsernameAlreadyExistsException, PasswordsDoNotMatchException, InvalidUsernameOrPasswordException {
        return userService.registerNewUser(email,name,surname,password,repeatPassword,role);
    }


    @GetMapping("/test")
    public Test test(){
        return new Test("bla");
    }

    @PostMapping("/editAccount")
    public AppUser editAccount(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                               @RequestParam String name,
                               @RequestParam String surname){
        Jws<Claims> jws = JwtValidator.validateToken(token);
        return userService.editAccount(jws.getBody().get("email").toString(),name,surname);
    }
}
