package chess;


/**
 * Thrown when an invalid FEN style string is used
 */
public class InvalidFENException extends Exception {
    public InvalidFENException(String errorMessage) {
        super(errorMessage);
    }
}