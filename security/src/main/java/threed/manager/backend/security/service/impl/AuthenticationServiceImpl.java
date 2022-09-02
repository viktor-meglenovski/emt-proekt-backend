package threed.manager.backend.security.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import threed.manager.backend.security.domain.JWTTokenResponse;
import threed.manager.backend.security.domain.repository.UserRepository;
import threed.manager.backend.security.service.AuthenticationService;
import threed.manager.backend.security.service.JwtTokenService;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final JwtTokenService jwtTokenService;
    private final PasswordEncoder passwordEncoder;


    @Override
    public JWTTokenResponse generateJWTToken(String username, String password) {
        return userRepository.findByUsername(username)
                .filter(account ->  passwordEncoder.matches(password, account.getPassword()))
                .map(account -> new JWTTokenResponse(jwtTokenService.generateToken(username)))
                .orElseThrow(() ->  new EntityNotFoundException("Account not found"));
    }
}
