package threed.manager.backend.registration.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import threed.manager.backend.registration.domain.AppUser;
import threed.manager.backend.registration.domain.Role;
import threed.manager.backend.registration.domain.exceptions.InvalidUsernameOrPasswordException;
import threed.manager.backend.registration.domain.exceptions.PasswordsDoNotMatchException;
import threed.manager.backend.registration.domain.exceptions.UserDoesNotExistException;
import threed.manager.backend.registration.domain.exceptions.UsernameAlreadyExistsException;
import threed.manager.backend.registration.domain.repository.UserRepository;
import threed.manager.backend.registration.service.UserService;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public AppUser registerNewUser(String email, String name, String surname, String password, String repeatPassword, String role) throws UsernameAlreadyExistsException {
        if (email==null || email.isEmpty()  || password==null || password.isEmpty())
            throw new InvalidUsernameOrPasswordException();
        if (!password.equals(repeatPassword))
            throw new PasswordsDoNotMatchException();
        if(this.userRepository.findByEmail(email).isPresent())
            throw new UsernameAlreadyExistsException(email);

        AppUser user = new AppUser(email,name,surname, passwordEncoder.encode(password),Role.valueOf(role));
        return userRepository.save(user);
    }
    @Override
    public AppUser findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(UserDoesNotExistException::new);
    }
}
