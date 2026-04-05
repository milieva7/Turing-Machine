package bg.tu_varna.sit.f24621691.project.io.exceptions;

public class TuringFileNotFoundException extends RuntimeException {
    public TuringFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public TuringFileNotFoundException(String message) {
        super(message);
    }
}
