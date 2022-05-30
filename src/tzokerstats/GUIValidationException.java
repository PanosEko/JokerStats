package tzokerstats;

//Exception that corresponds to error during a form validation
public class GUIValidationException extends Exception {

    public GUIValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public GUIValidationException(String message) {
        super(message);
    }
}
