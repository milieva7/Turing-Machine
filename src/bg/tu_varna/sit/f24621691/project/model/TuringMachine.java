package bg.tu_varna.sit.f24621691.project.model;

import bg.tu_varna.sit.f24621691.project.model.exceptions.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Класът представя детерминирана Машина на Тюринг.
 * Съхранява състояния, азбуки, преходи, начално състояние,
 * приемащи и отхвърлящи състояния, както и текущата лента при изпълнение.
 */
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

    /**
     * Създава нова Машина на Тюринг с подадено ID.
     *
     * @param id уникален идентификатор на машината
     */
    public TuringMachine(String id) {
        this.id = id;
        this.states = new HashSet<>();
        this.inputAlphabet = new HashSet<>();
        this.tapeAlphabet = new HashSet<>();
        this.acceptStates = new HashSet<>();
        this.rejectStates = new HashSet<>();
        this.transitions = new ArrayList<>();
    }

    /**
     * Добавя ново състояние към машината.
     *
     * @param state името на състоянието
     * @throws InvalidStateException ако състоянието е null или празно
     */
    public void addState(String state) {
        if (state == null || state.isBlank()) {
            throw new InvalidStateException("Състоянието не може да бъде празно!");
        }
        states.add(state);
    }

    /**
     * Задава началното състояние на машината.
     *
     * @param state името на началното състояние
     * @throws InvalidStateException ако състоянието не съществува в множеството от състояния
     */
    public void setStartState(String state) {
        if (!states.contains(state)) {
            throw new InvalidStateException("Състоянието '" + state + "' не е дефинирано!");
        }
        this.startState = state;
    }

    /**
     * Добавя приемащо състояние към машината.
     * Ако състоянието още не съществува, то се добавя и към общото множество от състояния.
     *
     * @param state името на приемащото състояние
     */
    public void addAcceptState(String state) {
        addState(state);
        acceptStates.add(state);
    }

    /**
     * Добавя отхвърлящо състояние към машината.
     * Ако състоянието още не съществува, то се добавя и към общото множество от състояния.
     *
     * @param state името на отхвърлящото състояние
     */
    public void addRejectState(String state) {
        addState(state);
        rejectStates.add(state);
    }

    /**
     * Добавя нов преход към машината.
     * Методът проверява дали началното и крайното състояние съществуват
     * и дали няма друг преход със същата двойка от състояние и входен символ.
     *
     * @param newTransition преходът, който ще бъде добавен
     * @throws InvalidStateException ако началното или крайното състояние не съществува
     * @throws NonDeterministicException ако вече има преход за същото състояние и символ
     */
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

    /**
     * Премахва преход по подадено начално състояние и символ за четене.
     *
     * @param state началното състояние на прехода
     * @param symbol символът, който се чете от лентата
     */
    public void removeTransition(String state, char symbol) {
        List<Transition> remaining = new ArrayList<>();

        for (Transition t : transitions) {
            if (!(t.getFromState().equals(state) && t.getReadSymbol() == symbol)) {
                remaining.add(t);
            }
        }

        this.transitions = remaining;
    }

    /**
     * Инициализира машината с входна дума.
     * Създава нова лента и задава текущото състояние като началното състояние.
     *
     * @param input входната дума, която ще бъде поставена върху лентата
     * @throws InvalidStateException ако няма зададено начално състояние
     */
    public void init(String input) {
        if (startState == null) {
            throw new InvalidStateException("Няма начално състояние!");
        }

        this.tape = new Tape(input);
        this.currentState = startState;
        this.haltedNoTransition = false;
    }

    /**
     * Изпълнява една стъпка от работата на машината.
     * Ако има подходящ преход, машината записва символ, мести главата
     * и сменя текущото състояние. Ако няма преход, машината спира.
     *
     * @throws MachineNotInitializedException ако машината не е инициализирана
     */
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

    /**
     * Нулира текущото изпълнение на машината.
     * Не премахва състояния, преходи или крайни състояния.
     * Нулира само лентата, текущото състояние и флага за липсващ преход.
     */
    public void reset() {
        this.tape = null;
        this.currentState = null;
        this.haltedNoTransition = false;
    }

    /**
     * Проверява дали машината е достигнала крайно състояние.
     * Машината спира, ако е в приемащо състояние, в отхвърлящо състояние
     * или ако няма валиден преход за текущата конфигурация.
     *
     * @return true ако машината е спряла, иначе false
     */
    public boolean isHalted() {
        return acceptStates.contains(currentState)
                || rejectStates.contains(currentState)
                || haltedNoTransition;
    }

    /**
     * Проверява дали машината е спряла поради липсващ преход.
     *
     * @return true ако машината е спряла заради липса на преход, иначе false
     */
    public boolean isHaltedNoTransition() {
        return haltedNoTransition;
    }

    /**
     * Форматира основната информация за машината и нейните преходи.
     *
     * @return текстово представяне на машината във формат, подходящ за преглед
     */
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

    /**
     * Връща кратко текстово представяне на машината.
     *
     * @return текст с ID-то на машината
     */
    @Override
    public String toString() {
        return "TuringMachine{id='" + id + "'}";
    }

    /**
     * Връща ID-то на машината.
     *
     * @return идентификатор на машината
     */
    public String getId() { return id; }

    /**
     * Връща текущото състояние на машината.
     *
     * @return текущото състояние
     */
    public String getCurrentState() { return currentState; }

    /**
     * Връща текущата лента на машината.
     *
     * @return лентата на машината
     */
    public Tape getTape() { return tape; }

    /**
     * Връща копие на списъка с преходи.
     *
     * @return списък с преходите на машината
     */
    public List<Transition> getTransitions() {
        return new ArrayList<>(transitions);
    }

    /**
     * Връща копие на множеството от състояния.
     *
     * @return множество със състоянията на машината
     */
    public Set<String> getStates() {
        return new HashSet<>(states);
    }

    /**
     * Връща началното състояние на машината.
     *
     * @return началното състояние
     */
    public String getStartState() {
        return startState;
    }

    /**
     * Връща копие на множеството от приемащи състояния.
     *
     * @return множество с приемащите състояния
     */
    public Set<String> getAcceptStates() {
        return new HashSet<>(acceptStates);
    }

    /**
     * Връща копие на множеството от отхвърлящи състояния.
     *
     * @return множество с отхвърлящите състояния
     */
    public Set<String> getRejectStates() {
        return new HashSet<>(rejectStates);
    }
}