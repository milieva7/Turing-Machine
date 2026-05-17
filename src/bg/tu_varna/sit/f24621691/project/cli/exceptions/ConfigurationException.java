package bg.tu_varna.sit.f24621691.project.cli.exceptions;

/**
 * Изключение, което се хвърля при грешка в конфигурацията
 * или при невалидни данни, подадени към команда.
 */
public class ConfigurationException extends RuntimeException {

    /**
     * Създава ново изключение със съобщение за грешка.
     *
     * @param message съобщението за грешката
     */
    public ConfigurationException(String message) {
        super(message);
    }
}