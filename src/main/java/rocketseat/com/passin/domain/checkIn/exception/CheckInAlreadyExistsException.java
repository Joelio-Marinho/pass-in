package rocketseat.com.passin.domain.checkIn.exception;

public class CheckInAlreadyExistsException extends RuntimeException{

    public CheckInAlreadyExistsException(String message) {
        super(message);
    }
}
