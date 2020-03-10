package ravi.labs.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Person does not exist")
public class PersonNotFoundException extends Exception {
    public PersonNotFoundException(String message) {
        super(message);
    }

}
