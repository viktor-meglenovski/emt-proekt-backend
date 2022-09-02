package threed.manager.backend.security.service;

import threed.manager.backend.security.domain.JWTTokenResponse;

public interface AuthenticationService {
    JWTTokenResponse generateJWTToken(String username, String password);
}
