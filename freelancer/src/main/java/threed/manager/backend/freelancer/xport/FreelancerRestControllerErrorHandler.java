package threed.manager.backend.freelancer.xport;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import threed.manager.backend.sharedkernel.security.exceptions.NotAuthorisedAccessException;

@RestControllerAdvice
public class FreelancerRestControllerErrorHandler {

    @ExceptionHandler(NotAuthorisedAccessException.class)
    public ResponseEntity<Void> handleTokenException(Exception e) {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}