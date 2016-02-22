package shakkipeli.exceptions;

/**
 * Exception thrown in case of invalid piece coordinates
 * @author Samuel
 */
public class InvalidCoordinates extends Exception {

    public InvalidCoordinates() {
        super("Invalid coordinates given");
    }

    public InvalidCoordinates(String msg) {
        super(msg);
    }
}
