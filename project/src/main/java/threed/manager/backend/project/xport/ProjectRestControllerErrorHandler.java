package threed.manager.backend.project.xport;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import threed.manager.backend.sharedkernel.security.exceptions.NotAuthorisedAccessException;

import javax.persistence.EntityNotFoundException;

@RestControllerAdvice
public class ProjectRestControllerErrorHandler {

    @ExceptionHandler(NotAuthorisedAccessException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity handleNotAuthorizedAccessException(NotAuthorisedAccessException e) {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}