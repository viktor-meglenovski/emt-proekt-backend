package threed.manager.backend.registration.xport;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import threed.manager.backend.registration.domain.AppUser;
import threed.manager.backend.registration.domain.exceptions.InvalidUsernameOrPasswordException;
import threed.manager.backend.registration.domain.exceptions.PasswordsDoNotMatchException;
import threed.manager.backend.registration.domain.exceptions.UsernameAlreadyExistsException;
import threed.manager.backend.registration.domain.repository.UserRepository;
import threed.manager.backend.registration.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
public class RegistrationRestController {

    private final UserService userService;

    @PostMapping("/register")
    public AppUser registerNewUser(@RequestParam String email,
                                   @RequestParam String name,
                                   @RequestParam String surname,
                                   @RequestParam String password,
                                   @RequestParam String repeatPassword,
                                   @RequestParam String role)throws UsernameAlreadyExistsException, PasswordsDoNotMatchException, InvalidUsernameOrPasswordException {
        return userService.registerNewUser(email,name,surname,password,repeatPassword,role);
    }
}
