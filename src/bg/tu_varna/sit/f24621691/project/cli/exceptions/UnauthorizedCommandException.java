package bg.tu_varna.sit.f24621691.project.cli.exceptions;

/**
 * Изключение, което се хвърля при опит за изпълнение на команда,
 * която не е позволена в текущото състояние на програмата.
 * Например при изпълнение на защитена команда без отворен файл.
 */
public class UnauthorizedCommandException extends RuntimeException {

    /**
     * Създава ново изключение със съобщение за грешка.
     *
     * @param message съобщението за грешката
     */
    public UnauthorizedCommandException(String message) {
        super(message);
    }
}