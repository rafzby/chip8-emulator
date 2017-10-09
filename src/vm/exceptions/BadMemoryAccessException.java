package vm.exceptions;

public class BadMemoryAccessException extends Exception {
    public BadMemoryAccessException(String message) {
        super(message);
    }
}
