package threed.manager.backend.security.xport;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import threed.manager.backend.security.domain.AuthenticationRequest;
import threed.manager.backend.security.service.AuthenticationService;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class AuthenticationRestController {

    private final AuthenticationService authenticationService;


    @PostMapping("/login")
    public ResponseEntity createCustomer(@RequestBody AuthenticationRequest request) {
        return new ResponseEntity<>(authenticationService.generateJWTToken(request.getUsername(), request.getPassword()), HttpStatus.OK);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity handleEntityNotFoundException(EntityNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
