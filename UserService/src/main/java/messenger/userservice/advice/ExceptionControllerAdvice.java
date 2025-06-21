package messenger.userservice.advice;

import messenger.userservice.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {
        @ExceptionHandler(UserNotFoundException.class)
        public ResponseEntity<String> exceptionUserNotFoundException(UserNotFoundException e) {;
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
}
