public class EmptyQueueException extends RuntimeException {
    
    private String message;
    private static final String DEFAULT_MESSAGE = "";

    public EmptyQueueException() {
        this(DEFAULT_MESSAGE);
    }
    
    public EmptyQueueException(String message) {
        this.message = message; 
    }

    public String toString() {
        return this.message;
    }
}
