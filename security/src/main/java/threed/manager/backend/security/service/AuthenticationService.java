package threed.manager.backend.security.service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import threed.manager.backend.security.domain.JWTTokenResponse;
import threed.manager.backend.security.domain.repository.UserRepository;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final JwtTokenService jwtTokenService;
    private final PasswordEncoder passwordEncoder;


    public JWTTokenResponse generateJWTToken(String username, String password) {
        return userRepository.findByEmail(username)
                .filter(account ->  passwordEncoder.matches(password, account.getPassword()))
                .map(account -> new JWTTokenResponse(jwtTokenService.generateToken(username,account.getRole())))
                .orElseThrow(() ->  new EntityNotFoundException("Account not found"));
    }
}
