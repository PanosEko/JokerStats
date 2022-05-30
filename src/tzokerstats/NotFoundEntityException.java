package tzokerstats;

//Exception that denotes error due to non existance of a data item
public class NotFoundEntityException extends Exception {

    public NotFoundEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundEntityException(String message) {
        super(message);
    }
}
