package ravi.labs.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE , reason = "Person with same first and last name exist or " +
        "Person firsname and lastname both cannot be empty")
public class PersonConstraintVoilationException extends Exception {
    public PersonConstraintVoilationException(String message) {
        super(message);
    }

}
