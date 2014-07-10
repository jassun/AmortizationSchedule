package exercises;
public class InvalidValueException extends Exception {

    private InvalidValueException() {
    }

    public InvalidValueException(String arg0) {
        super(arg0);
    }

    public InvalidValueException(Throwable arg0) {
        super(arg0);
    }

    public InvalidValueException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

}
