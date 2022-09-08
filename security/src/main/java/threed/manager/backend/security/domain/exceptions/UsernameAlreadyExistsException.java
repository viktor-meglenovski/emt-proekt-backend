package threed.manager.backend.security.domain.exceptions;

import lombok.Getter;

@Getter
public class UsernameAlreadyExistsException extends RuntimeException{
    private String email;
    public UsernameAlreadyExistsException(String email){
        super();
        this.email=email;
    }
}
