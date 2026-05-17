package bg.tu_varna.sit.f24621691.project.model;

import bg.tu_varna.sit.f24621691.project.model.exceptions.*;

/**
 * Класът представя един преход в Машина на Тюринг.
 * Всеки преход съдържа начално състояние, символ за четене,
 * крайно състояние, символ за запис и посока на движение.
 */
public class Transition {
    private final String fromState;
    private final char readSymbol;
    private final String toState;
    private final char writeSymbol;
    private final char direction; // L, R, S

    /**
     * Създава нов преход с подадените параметри.
     *
     * @param fromState началното състояние на прехода
     * @param readSymbol символът, който се чете от лентата
     * @param toState състоянието, към което се преминава
     * @param writeSymbol символът, който се записва върху лентата
     * @param direction посоката на движение на главата
     * @throws InvalidStateException ако началното или крайното състояние е null или празно
     * @throws InvalidDirectionException ако посоката е различна от L, R или S
     */
    public Transition(String fromState, char readSymbol,
                      String toState, char writeSymbol, char direction) {

        //Проверка за валидни състояния
        if (fromState == null || fromState.isBlank()
                || toState == null || toState.isBlank()) {
            throw new InvalidStateException("Състоянията не могат да бъдат празни!");
        }

        //нормализиране
        direction = Character.toUpperCase(direction);

        //Проверка за валидна посока
        if (direction != 'L' && direction != 'R' && direction != 'S') {
            throw new InvalidDirectionException("Невалидна посока: " + direction);
        }

        this.fromState = fromState;
        this.readSymbol = readSymbol;
        this.toState = toState;
        this.writeSymbol = writeSymbol;
        this.direction = direction;
    }

    /**
     * Връща началното състояние на прехода.
     *
     * @return началното състояние
     */
    public String getFromState() {
        return fromState;
    }

    /**
     * Връща символа, който се чете от лентата.
     *
     * @return символът за четене
     */
    public char getReadSymbol() {
        return readSymbol;
    }

    /**
     * Връща крайното състояние на прехода.
     *
     * @return състоянието, към което се преминава
     */
    public String getToState() {
        return toState;
    }

    /**
     * Връща символа, който се записва върху лентата.
     *
     * @return символът за запис
     */
    public char getWriteSymbol() {
        return writeSymbol;
    }

    /**
     * Връща посоката на движение на главата.
     *
     * @return посоката на движение
     */
    public char getDirection() {
        return direction;
    }

    /**
     * Връща текстово представяне на прехода.
     *
     * @return преходът във формат "q, a -> q2, b, R"
     */
    @Override
    public String toString() {
        return fromState + ", " + readSymbol +
                " -> " + toState + ", " +
                writeSymbol + ", " + direction;
    }
}