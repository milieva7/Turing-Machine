package bg.tu_varna.sit.f24621691.project.model.exceptions;

/**
 * Изключение, което се хвърля при опит за изпълнение на операция
 * върху Машина на Тюринг, която още не е инициализирана с входна дума.
 */
public class MachineNotInitializedException extends RuntimeException {

    /**
     * Създава ново изключение със съобщение за грешка.
     *
     * @param message съобщението за грешката
     */
    public MachineNotInitializedException(String message) {
        super(message);
    }
}