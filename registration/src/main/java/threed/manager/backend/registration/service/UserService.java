package threed.manager.backend.registration.service;

import threed.manager.backend.registration.domain.AppUser;
import threed.manager.backend.registration.domain.exceptions.UsernameAlreadyExistsException;

public interface UserService {
    AppUser findByEmail(String email);
    AppUser registerNewUser(String email, String name, String surname, String password, String repeatPassword, String role) throws UsernameAlreadyExistsException;
}
