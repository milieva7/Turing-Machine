package bg.tu_varna.sit.f24621691.project.model;

import bg.tu_varna.sit.f24621691.project.model.exceptions.*;

public class Transition {
    private final String fromState;
    private final char readSymbol;
    private final String toState;
    private final char writeSymbol;
    private final char direction; // L, R, S

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

    public String getFromState() {
        return fromState;
    }

    public char getReadSymbol() {
        return readSymbol;
    }

    public String getToState() {
        return toState;
    }

    public char getWriteSymbol() {
        return writeSymbol;
    }

    public char getDirection() {
        return direction;
    }

    @Override
    public String toString() {
        return fromState + ", " + readSymbol +
                " -> " + toState + ", " +
                writeSymbol + ", " + direction;
    }
}