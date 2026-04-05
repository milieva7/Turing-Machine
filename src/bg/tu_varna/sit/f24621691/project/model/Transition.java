package bg.tu_varna.sit.f24621691.project.model;

import bg.tu_varna.sit.f24621691.project.model.exceptions.*;

public class Transition {
    private String fromState;
    private char readSymbol;
    private String toState;
    private char writeSymbol;
    private char direction; // L, R, S

    public Transition(String fromState, char readSymbol, String toState, char writeSymbol, char direction) {
        // Проверка за валидна посока
        if (direction != 'L' && direction != 'R' && direction != 'S') {
            throw new InvalidDirectionException("Невалидна посока: '" + direction + "'. Позволени са само L, R, S.");
        }

        //Можеш да добавиш и проверка за null на състоянията, ако искаш да си бетон
        if (fromState == null || toState == null) {
            throw new InvalidStateException("Състоянията не могат да бъдат null!");
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
        return fromState + ", " + readSymbol + " -> " + toState + ", " + writeSymbol + ", " + direction;
    }
}