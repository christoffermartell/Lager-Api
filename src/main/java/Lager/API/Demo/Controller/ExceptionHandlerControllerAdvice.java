package Lager.API.Demo.Controller;

import Lager.API.Demo.exception.BadRequestException;
import Lager.API.Demo.exception.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleBadRequestException(BadRequestException ex) {
        return new ResponseEntity<>(ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

}
