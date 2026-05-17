package bg.tu_varna.sit.f24621691.project.model.exceptions;

/**
 * Изключение, което се хвърля при опит за добавяне на недетерминиран преход.
 * Това се случва, когато вече има преход за същата двойка
 * от състояние и символ за четене.
 */
public class NonDeterministicException extends RuntimeException {

    /**
     * Създава ново изключение със съобщение за грешка.
     *
     * @param message съобщението за грешката
     */
    public NonDeterministicException(String message) {
        super(message);
    }
}