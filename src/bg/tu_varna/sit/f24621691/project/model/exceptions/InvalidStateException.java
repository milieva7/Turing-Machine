package bg.tu_varna.sit.f24621691.project.model.exceptions;

/**
 * Изключение, което се хвърля при невалидно състояние на Машина на Тюринг.
 * Използва се при празно, null или недефинирано състояние.
 */
public class InvalidStateException extends RuntimeException {

    /**
     * Създава ново изключение със съобщение за грешка.
     *
     * @param message съобщението за грешката
     */
    public InvalidStateException(String message) {
        super(message);
    }
}