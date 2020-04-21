package lab8;

public class IncorrectEntryException extends Exception {
    private static final long serialVersionUID = 0L;

    public IncorrectEntryException() {
        super();
    }
    public IncorrectEntryException(String error) {
        super(error);
    }
}