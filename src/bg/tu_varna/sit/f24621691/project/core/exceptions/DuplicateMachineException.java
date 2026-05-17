package bg.tu_varna.sit.f24621691.project.core.exceptions;

/**
 * Изключение, което се хвърля при опит за добавяне на машина
 * с ID, което вече съществува в системата.
 */
public class DuplicateMachineException extends RuntimeException {

    /**
     * Създава ново изключение със съобщение за грешка.
     *
     * @param message съобщението за грешката
     */
    public DuplicateMachineException(String message) {
        super(message);
    }
}