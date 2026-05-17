package bg.tu_varna.sit.f24621691.project.model.exceptions;

/**
 * Изключение, което се хвърля при подадена невалидна посока на движение.
 * Валидните посоки за Машина на Тюринг са L, R и S.
 */
public class InvalidDirectionException extends RuntimeException {

    /**
     * Създава ново изключение със съобщение за грешка.
     *
     * @param message съобщението за грешката
     */
    public InvalidDirectionException(String message) {
        super(message);
    }
}