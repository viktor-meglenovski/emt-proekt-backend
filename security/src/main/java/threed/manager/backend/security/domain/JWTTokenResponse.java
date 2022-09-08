package threed.manager.backend.security.domain;

import lombok.Data;

@Data
public class JWTTokenResponse {
    private String token;
    public JWTTokenResponse(String token) {
        this.token = token;
    }

}
