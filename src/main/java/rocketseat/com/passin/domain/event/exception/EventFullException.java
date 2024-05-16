package rocketseat.com.passin.domain.event.exception;

public class EventFullException extends RuntimeException{

    public EventFullException(String message){
        super(message);
    }
}
