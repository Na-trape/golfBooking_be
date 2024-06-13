package fontys.sem3.school.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class LicenseAlreadyExistsException extends ResponseStatusException {
    public LicenseAlreadyExistsException() {
        super(HttpStatus.BAD_REQUEST, "License_ALREADY_EXISTS");
    }
}
