package rocketseat.com.passin.Config;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import rocketseat.com.passin.domain.event.exception.EventNotFoudException;

@ControllerAdvice
public class ExceptionEntityHandler {

    @ExceptionHandler(EventNotFoudException.class)
    public ResponseEntity handleEventNotFound(EventNotFoudException ex){

        return ResponseEntity.notFound().build();
    }
}
