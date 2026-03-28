package bg.tu_varna.sit.f24621691.project.model;

public class Transition {
    private String fromState;
    private char readSymbol;
    private String toState;
    private char writeSymbol;
    private char direction; // L, R, S

    public Transition(String fromState, char readSymbol, String toState, char writeSymbol, char direction) {
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