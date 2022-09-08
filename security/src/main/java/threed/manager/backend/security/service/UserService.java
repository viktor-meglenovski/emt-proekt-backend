package threed.manager.backend.security.service;


import threed.manager.backend.security.domain.AppUser;
import threed.manager.backend.security.domain.exceptions.UsernameAlreadyExistsException;

public interface UserService {
    AppUser findByEmail(String email);
    AppUser registerNewUser(String email, String name, String surname, String password, String repeatPassword, String role) throws UsernameAlreadyExistsException;
    AppUser editAccount(String email, String name, String surname);
}
