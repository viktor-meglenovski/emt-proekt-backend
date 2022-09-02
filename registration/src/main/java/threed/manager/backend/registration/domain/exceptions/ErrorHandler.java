package threed.manager.backend.registration.domain.exceptions;

import lombok.Data;

@Data
public class ErrorHandler {
    private String errorInformation;

    public ErrorHandler(){}
    public ErrorHandler(String errorInformation) {
        this.errorInformation = errorInformation;
    }
}
