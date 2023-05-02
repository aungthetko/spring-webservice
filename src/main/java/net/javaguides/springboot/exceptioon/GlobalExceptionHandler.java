package net.javaguides.springboot.exceptioon;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleUserNotFoundException(UserNotFoundException
                                                                 exception,
                                                                    WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now().toString(),
                exception.getMessage(),
                request.getDescription(false),
                "USER_NOT_FOUND"
        );
        return new ResponseEntity<>(errorDetails, NOT_FOUND);
    }

    @ExceptionHandler(EmailAlreadyTakenException.class)
    public ResponseEntity<ErrorDetails> handleEmailExistException(EmailAlreadyTakenException exception,
                                                                  WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now().toString(),
                exception.getMessage(),
                request.getDescription(false),
                "USER_EMAIL_ALREADY_EXIST"
        );
        return new ResponseEntity<>(errorDetails, BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleException(Exception exception,
                                                                  WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now().toString(),
                exception.getMessage(),
                request.getDescription(false),
                "INTERNAL_SERVER_ERROR"
        );
        return new ResponseEntity<>(errorDetails, INTERNAL_SERVER_ERROR);
    }
}
