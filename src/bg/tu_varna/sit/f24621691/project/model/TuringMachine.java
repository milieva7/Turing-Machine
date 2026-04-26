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
    private Set<Character> tapeAlphabet; //Gamma
    private String startState; // q0
    private Set<String> acceptStates; // F
    private Set<String> rejectStates; // R
    private List<Transition> transitions; // delta

    private Tape tape;
    private String currentState;

    public TuringMachine(String id) {
        this.id = id;
        this.states = new HashSet<>();
        this.inputAlphabet = new HashSet<>();
        this.tapeAlphabet = new HashSet<>();
        this.acceptStates = new HashSet<>();
        this.rejectStates = new HashSet<>();
        this.transitions = new ArrayList<>();
    }


    // Добавя ново състояние към машината
    public void addState(String state) {
        states.add(state);
    }


    //Задава начално състояние
    public void setStartState(String state) {
        if (!states.contains(state)) {
            throw new InvalidStateException("Състоянието '" + state + "' не е дефинирано в Q!");
        }
        this.startState = state;
    }


    //Добавя приемно състояние
    public void addAcceptState(String state) {
        states.add(state);
        acceptStates.add(state);
    }


    // Добавя отказно състояние
    public void addRejectState(String state) {
        states.add(state);
        rejectStates.add(state);
    }

    /*
      Добавя преход,проверява за детерминираност;
      не позволява два прехода за една и съща двойка състояние, символ
     */
    public void addTransition(Transition newTransition) {
        for (Transition t : transitions) {
            if (t.getFromState().equals(newTransition.getFromState()) &&
                    t.getReadSymbol() == newTransition.getReadSymbol()) {
                throw new NonDeterministicException("Вече има преход за ("
                        + t.getFromState() + ", " + t.getReadSymbol() + ")!");
            }
        }
        transitions.add(newTransition);
    }

    // Премахва преход за дадено състояние и символ
    public void removeTransition(String state, char symbol) {
        // Нов празен списък
        List<Transition> remainingTransitions = new ArrayList<>();

        //Минаваме през стария списък
        for (Transition t : transitions) {
            //Ако преходът не е този, който търсим
            if (!(t.getFromState().equals(state) && t.getReadSymbol() == symbol)) {
                //го добавяме в новия списък
                remainingTransitions.add(t);
            }
        }

        //Заменяме стария списък с новия
        this.transitions = remainingTransitions;
    }


    //Инициализира машината с входна дума
    public void init(String input) {
        if (startState == null) {
            throw new InvalidStateException("Машината не може да стартира без зададено начално състояние!");
        }
        this.tape = new Tape(input);
        this.currentState = startState;
    }


    //Изпълнява една стъпка от работата на машината
    public void step() {
        //Ако някой викне step без init се вика exception
        if (this.tape == null) {
            throw new MachineNotInitializedException("Опит за стъпка без инициализирана лента!");
        }

        if (isHalted()) return;

        char currentSymbol = tape.read();
        Transition match = null;

        for (Transition t : transitions) {
            if (t.getFromState().equals(currentState) && t.getReadSymbol() == currentSymbol) {
                match = t;
                break;
            }
        }

        if (match != null) {
            tape.write(match.getWriteSymbol());
            tape.move(match.getDirection());
            this.currentState = match.getToState();
        } else {
            //Ако няма преход, машината спира
            this.currentState = "HALTED_NO_TRANSITION";
        }
    }


    public boolean isHalted() {
        return acceptStates.contains(currentState) || rejectStates.contains(currentState) ||
                "HALTED_NO_TRANSITION".equals(currentState);
    }

    public String getId() {
        return id;
    }

    public String getCurrentState() {

        return currentState;
    }

    public Tape getTape() {
        return tape;
    }

    //Връща списъка с всички преходи
    public List<Transition> getTransitions() {
        return transitions;
    }

    //Връща всички състояния
    public Set<String> getStates() {
        return states;
    }

    //Връща началното състояние на машината
    public String getStartState() {
        return startState;
    }

    //Връща списък с всички състояния, в които машината спира и приема думата
    public Set<String> getAcceptStates() {
        return acceptStates;
    }

    //Връща списък със състоянията, в които машината спира и отхвърля думата
    public Set<String> getRejectStates() {
        return rejectStates;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        //Записваме ID-то с префикс TM
        sb.append("TM: ").append(id).append("\n");

        //Записваме преходите във формат
        for (Transition t : transitions) {
            sb.append(t.getFromState()).append(", ")
                    .append(t.getReadSymbol()).append(" -> ")
                    .append(t.getToState()).append(", ")
                    .append(t.getWriteSymbol()).append(", ")
                    .append(t.getDirection()).append("\n");
        }

        //Всички състояния накрая
        if (!states.isEmpty()) {
            sb.append(String.join(",", states)).append("\n");
        }

        //начално, приемащи и отхвърлящи, ги добавяме като последен ред,
        if (startState != null) {
            sb.append("Start: ").append(startState).append("\n");
        }

        return sb.toString().trim();
    }
}