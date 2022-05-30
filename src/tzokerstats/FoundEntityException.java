package tzokerstats;

//Exception that denotes error due to existance of a data item
public class FoundEntityException extends Exception {
    public FoundEntityException(String message, Throwable cause) {
        super(message, cause);
    }
    public FoundEntityException(String message) {
        super(message);
    }
}
