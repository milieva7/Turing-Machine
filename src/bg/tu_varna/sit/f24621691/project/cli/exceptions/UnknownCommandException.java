package bg.tu_varna.sit.f24621691.project.cli.exceptions;

/**
 * Изключение, което се хвърля при въведена непозната команда.
 * Използва се, когато потребителят въведе команда, която не съществува
 * в списъка с поддържани команди.
 */
public class UnknownCommandException extends RuntimeException {

    /**
     * Създава ново изключение със съобщение за грешка.
     *
     * @param message съобщението за грешката
     */
    public UnknownCommandException(String message) {
        super(message);
    }
}