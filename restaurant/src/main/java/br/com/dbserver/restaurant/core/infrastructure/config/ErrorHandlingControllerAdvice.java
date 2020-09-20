package br.com.dbserver.restaurant.core.infrastructure.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.dbserver.restaurant.core.domain.RestaurantException;

@RestControllerAdvice
public class ErrorHandlingControllerAdvice {

    @ExceptionHandler(RestaurantException.class)
    public ResponseEntity<RestaurantError> handleHangmanException(RestaurantException ex) {
        return new ResponseEntity<>(new RestaurantError(ex.getMessage()),
                HttpStatus.BAD_REQUEST);
    }
}
