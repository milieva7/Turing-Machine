package bg.tu_varna.sit.f24621691.project.io.exceptions;

/**
 * Изключение, което се хвърля при проблем при работа с файл.
 * Използва се при невалиден път, липса на достъп или грешка при четене/запис.
 */
public class TuringFileNotFoundException extends RuntimeException {

    /**
     * Създава ново изключение със съобщение за грешка и първоначална причина.
     *
     * @param message съобщението за грешката
     * @param cause първоначалната причина за грешката
     */
    public TuringFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Създава ново изключение само със съобщение за грешка.
     *
     * @param message съобщението за грешката
     */
    public TuringFileNotFoundException(String message) {
        super(message);
    }
}