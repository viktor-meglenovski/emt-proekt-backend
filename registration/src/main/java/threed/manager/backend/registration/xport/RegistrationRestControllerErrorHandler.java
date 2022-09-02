package threed.manager.backend.registration.xport;

import org.h2.util.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import threed.manager.backend.registration.domain.exceptions.ErrorHandler;
import threed.manager.backend.registration.domain.exceptions.InvalidUsernameOrPasswordException;
import threed.manager.backend.registration.domain.exceptions.PasswordsDoNotMatchException;
import threed.manager.backend.registration.domain.exceptions.UsernameAlreadyExistsException;

@RestControllerAdvice
public class RegistrationRestControllerErrorHandler {

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


}
