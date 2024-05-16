package rocketseat.com.passin.Config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import rocketseat.com.passin.DTO.exception.ErrorResponseDTO;
import rocketseat.com.passin.domain.attendee.exception.AttendeeAlreadyExistException;
import rocketseat.com.passin.domain.attendee.exception.AttendeeNotFoundException;
import rocketseat.com.passin.domain.checkIn.exception.CheckInAlreadyExistsException;
import rocketseat.com.passin.domain.event.exception.EventFullException;
import rocketseat.com.passin.domain.event.exception.EventNotFoudException;

@ControllerAdvice
public class ExceptionEntityHandler {

    @ExceptionHandler(EventNotFoudException.class)
    public ResponseEntity handleEventNotFound(EventNotFoudException ex){

        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(AttendeeNotFoundException.class)
    public ResponseEntity handleAttendeeNotFound(AttendeeNotFoundException ex){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(AttendeeAlreadyExistException.class)
    public ResponseEntity handleAttendeeAlreadyExist(AttendeeAlreadyExistException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponseDTO(ex.getMessage()));
    }

    @ExceptionHandler(CheckInAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDTO> handleCheckInAlreadyExists (CheckInAlreadyExistsException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponseDTO(ex.getMessage()));
    }

    @ExceptionHandler(EventFullException.class)
    public ResponseEntity<ErrorResponseDTO> handleEventFull(EventFullException ex){
        return ResponseEntity.badRequest().body(new ErrorResponseDTO(ex.getMessage()));
    }
}
