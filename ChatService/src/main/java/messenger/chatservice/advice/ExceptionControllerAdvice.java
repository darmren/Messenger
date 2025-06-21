package messenger.chatservice.advice;

import messenger.chatservice.exception.UserIdNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {
        @ExceptionHandler(UserIdNotFoundException.class)
        public ResponseEntity<String> exceptionUserIdNotFoundException(UserIdNotFoundException e) {;
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
}
