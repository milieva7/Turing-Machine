package bg.tu_varna.sit.f24621691.project.model;

import bg.tu_varna.sit.f24621691.project.model.exceptions.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TuringMachine {
    private String id;
    private Set<String> states; // Q
    private Set<Character> inputAlphabet; // Sigma
    private Set<Character> tapeAlphabet; // Gamma
    private String startState; // q0
    private Set<String> acceptStates; // F
    private Set<String> rejectStates; // R
    private List<Transition> transitions; // delta

    private Tape tape;
    private String currentState;
    private boolean haltedNoTransition;

    public TuringMachine(String id) {
        this.id = id;
        this.states = new HashSet<>();
        this.inputAlphabet = new HashSet<>();
        this.tapeAlphabet = new HashSet<>();
        this.acceptStates = new HashSet<>();
        this.rejectStates = new HashSet<>();
        this.transitions = new ArrayList<>();
    }

    //Добавя ново състояние
    public void addState(String state) {
        if (state == null || state.isBlank()) {
            throw new InvalidStateException("Състоянието не може да бъде празно!");
        }
        states.add(state);
    }

    //Задава начално състояние
    public void setStartState(String state) {
        if (!states.contains(state)) {
            throw new InvalidStateException("Състоянието '" + state + "' не е дефинирано!");
        }
        this.startState = state;
    }

    //Приемащо състояние
    public void addAcceptState(String state) {
        addState(state);
        acceptStates.add(state);
    }

    //Отказно състояние
    public void addRejectState(String state) {
        addState(state);
        rejectStates.add(state);
    }

    //Добавя преход
    public void addTransition(Transition newTransition) {

        // Проверка дали състоянията съществуват
        if (!states.contains(newTransition.getFromState())) {
            throw new InvalidStateException("Невалидно начално състояние!");
        }

        if (!states.contains(newTransition.getToState())) {
            throw new InvalidStateException("Невалидно крайно състояние!");
        }

        //Проверка за детерминираност
        for (Transition t : transitions) {
            if (t.getFromState().equals(newTransition.getFromState())
                    && t.getReadSymbol() == newTransition.getReadSymbol()) {
                throw new NonDeterministicException("Вече има такъв преход!");
            }
        }

        //Добавяне към азбуките
        inputAlphabet.add(newTransition.getReadSymbol());
        tapeAlphabet.add(newTransition.getReadSymbol());
        tapeAlphabet.add(newTransition.getWriteSymbol());

        transitions.add(newTransition);
    }

    //Премахва преход
    public void removeTransition(String state, char symbol) {
        List<Transition> remaining = new ArrayList<>();

        for (Transition t : transitions) {
            if (!(t.getFromState().equals(state) && t.getReadSymbol() == symbol)) {
                remaining.add(t);
            }
        }

        this.transitions = remaining;
    }

    //Инициализация
    public void init(String input) {
        if (startState == null) {
            throw new InvalidStateException("Няма начално състояние!");
        }

        this.tape = new Tape(input);
        this.currentState = startState;
        this.haltedNoTransition = false;
    }

    //Една стъпка
    public void step() {
        if (tape == null) {
            throw new MachineNotInitializedException("Машината не е стартирана!");
        }

        if (isHalted()) return;

        char symbol = tape.read();
        Transition match = null;

        for (Transition t : transitions) {
            if (t.getFromState().equals(currentState)
                    && t.getReadSymbol() == symbol) {
                match = t;
                break;
            }
        }

        if (match != null) {
            tape.write(match.getWriteSymbol());
            tape.move(match.getDirection());
            currentState = match.getToState();
        } else {
            haltedNoTransition = true;
        }
    }

    //Проверка дали е спряла
    public boolean isHalted() {
        return acceptStates.contains(currentState)
                || rejectStates.contains(currentState)
                || haltedNoTransition;
    }

    //Форматиране
    public String formatMachine() {
        StringBuilder sb = new StringBuilder();

        sb.append("TM: ").append(id).append("\n");

        for (Transition t : transitions) {
            sb.append(t.getFromState()).append(", ")
                    .append(t.getReadSymbol()).append(" -> ")
                    .append(t.getToState()).append(", ")
                    .append(t.getWriteSymbol()).append(", ")
                    .append(t.getDirection()).append("\n");
        }

        return sb.toString().trim();
    }

    @Override
    public String toString() {
        return "TuringMachine{id='" + id + "'}";
    }

    public String getId() { return id; }
    public String getCurrentState() { return currentState; }
    public Tape getTape() { return tape; }

    public List<Transition> getTransitions() {
        return new ArrayList<>(transitions);
    }

    public Set<String> getStates() {
        return new HashSet<>(states);
    }

    public String getStartState() {
        return startState;
    }

    public Set<String> getAcceptStates() {
        return new HashSet<>(acceptStates);
    }

    public Set<String> getRejectStates() {
        return new HashSet<>(rejectStates);
    }
}