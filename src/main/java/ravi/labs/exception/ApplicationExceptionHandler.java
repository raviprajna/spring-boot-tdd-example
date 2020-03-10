package ravi.labs.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ApplicationExceptionHandler {

    Logger log = LoggerFactory.getLogger(ApplicationExceptionHandler.class);

    @ExceptionHandler(PersonNotFoundException.class)
    public ResponseEntity<?> handlePersonNotFoundException(HttpServletRequest request, PersonNotFoundException e){
        log.error("URI : {} ERROR : {} ", request.getRequestURI(), e.getMessage() );
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(
                        HttpStatus.NOT_FOUND.value(),
                        HttpStatus.NOT_FOUND.getReasonPhrase(),
                        e.getMessage()));
    }

    @ExceptionHandler(PersonConstraintVoilationException.class)
    public ResponseEntity<?> handlePersonConstraintsVoilation(HttpServletRequest request, PersonNotFoundException e){
        log.error("URI : {} ERROR : {} ", request.getRequestURI(), e.getMessage() );
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(
                        HttpStatus.NOT_FOUND.value(),
                        HttpStatus.NOT_FOUND.getReasonPhrase(),
                        e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> handleBadRequest(HttpServletRequest request, MethodArgumentTypeMismatchException e){
        log.error("URI : {} ERROR : {} ", request.getRequestURI(), e.getMessage() );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        HttpStatus.BAD_REQUEST.getReasonPhrase(),
                        e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleBadRequest(HttpServletRequest request, MethodArgumentNotValidException e){
        log.error("URI : {} ERROR : {} ", request.getRequestURI(), e.getMessage() );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        HttpStatus.BAD_REQUEST.getReasonPhrase(),
                        e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleAllException(HttpServletRequest request, Exception e){
        log.error("URI : {} ERROR : {} ", request.getRequestURI(), e.getMessage() );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                        e.getMessage()
                        ));
    }

}
