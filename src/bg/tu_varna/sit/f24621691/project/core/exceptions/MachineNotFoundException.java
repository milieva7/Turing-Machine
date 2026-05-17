package bg.tu_varna.sit.f24621691.project.core.exceptions;

/**
 * Изключение, което се хвърля при опит за достъп до машина,
 * която не е заредена или не съществува в системата.
 */
public class MachineNotFoundException extends RuntimeException {

    /**
     * Създава ново изключение със съобщение за грешка.
     *
     * @param message съобщението за грешката
     */
    public MachineNotFoundException(String message) {
        super(message);
    }
}