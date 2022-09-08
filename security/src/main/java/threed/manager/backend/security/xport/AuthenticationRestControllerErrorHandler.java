package threed.manager.backend.security.xport;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import threed.manager.backend.security.domain.exceptions.ErrorHandler;
import threed.manager.backend.security.domain.exceptions.InvalidUsernameOrPasswordException;
import threed.manager.backend.security.domain.exceptions.PasswordsDoNotMatchException;
import threed.manager.backend.security.domain.exceptions.UsernameAlreadyExistsException;

import javax.persistence.EntityNotFoundException;

@RestControllerAdvice
public class AuthenticationRestControllerErrorHandler {

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.OK)
    public ErrorHandler handleUsernameAlreadyExistsException(UsernameAlreadyExistsException e) {
        return new ErrorHandler(e.getEmail());
    }

    @ExceptionHandler(PasswordsDoNotMatchException.class)
    @ResponseStatus(HttpStatus.OK)
    public ErrorHandler handlePasswordsDoNotMatchException(PasswordsDoNotMatchException e) {
        return new ErrorHandler("Passwords");
    }


    @ExceptionHandler(InvalidUsernameOrPasswordException.class)
    @ResponseStatus(HttpStatus.OK)
    public ErrorHandler handleInvalidUsernameOrPasswordException(InvalidUsernameOrPasswordException e) {
        return new ErrorHandler("Invalid");
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity handleEntityNotFoundException(EntityNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }


}
