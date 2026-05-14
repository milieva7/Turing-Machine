package bg.tu_varna.sit.f24621691.project.core;

import bg.tu_varna.sit.f24621691.project.model.Transition;
import bg.tu_varna.sit.f24621691.project.model.TuringMachine;
import bg.tu_varna.sit.f24621691.project.core.exceptions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 Класът управлява всички заредени Машини на Тюринг в програмата.
 Съхранява ги в списък и позволява достъп до тях чрез уникално ID.
 */
public class MachineManager {
    //Тук пазим машините
    private Map<String, TuringMachine> machines;

    public MachineManager() {
        this.machines = new HashMap<>();
    }

    /*
      Добавя нова машина в списъка.
      Проверява дали вече не съществува машина със същото ID.
     */
    public void addMachine(TuringMachine machine) {
        if (machine == null) {
            throw new IllegalArgumentException("Не може да добавите null обект!");
        }

        if (machines.containsKey(machine.getId())) {
            throw new DuplicateMachineException("Машина с ID '" + machine.getId() + "' вече е заредена в системата!");
        }

        machines.put(machine.getId(), machine);
    }

    //Намира и връща машина по нейното ID
    public TuringMachine getMachine(String id) {
        if (!machines.containsKey(id)) {
            throw new MachineNotFoundException("Не съществува заредена машина с ID '" + id + "'.");
        }

        return machines.get(id);
    }

    //Връща списък с всички налични ID-та на заредените машини
    public Set<String> getAllMachineIds() {
        return machines.keySet();
    }

    //Проверява дали в момента има заредени машини.
    public boolean hasMachines() {
        return !machines.isEmpty();
    }

    //Подготвя всички машини за запис във файл
    public List<String> getSerializableData() {
        List<String> data = new ArrayList<>();

        for (TuringMachine tm : machines.values()) {
            data.addAll(serializeMachine(tm));
            data.add("---");
        }

        return data;
    }

    //Подготвя една конкретна машина за запис във файл
    public List<String> getSerializableDataForMachine(String id) {
        TuringMachine tm = getMachine(id);

        List<String> data = new ArrayList<>();
        data.addAll(serializeMachine(tm));
        data.add("---");

        return data;
    }

    //Превръща една машина в редове, подходящи за запис във файл
    private List<String> serializeMachine(TuringMachine tm) {
        List<String> data = new ArrayList<>();

        //Записваме ID-то на машината
        data.add("TM: " + tm.getId());

        //Записваме всички преходи
        for (Transition t : tm.getTransitions()) {
            data.add(t.toString());
        }

        //Записваме всички състояния на един ред
        if (!tm.getStates().isEmpty()) {
            data.add(String.join(",", tm.getStates()));
        }

        //Записваме началното състояние, ако е зададено
        if (tm.getStartState() != null) {
            data.add("Start: " + tm.getStartState());
        }

        //Записваме приемащите състояния, ако има такива
        if (!tm.getAcceptStates().isEmpty()) {
            data.add("Accept: " + String.join(",", tm.getAcceptStates()));
        }

        //Записваме отхвърлящите състояния, ако има такива
        if (!tm.getRejectStates().isEmpty()) {
            data.add("Reject: " + String.join(",", tm.getRejectStates()));
        }

        return data;
    }

    //Изчиства всички заредени машини
    public void clear() {
        this.machines.clear();
    }

    //Създава машина от редовете, прочетени от файл
    public String deserializeAndAdd(List<String> lines) {
        if (lines == null || lines.isEmpty()) {
            return null;
        }

        String id = lines.get(0).replace("TM:", "").trim();
        TuringMachine tm = new TuringMachine(id);

        List<Transition> loadedTransitions = new ArrayList<>();
        List<String> loadedAcceptStates = new ArrayList<>();
        List<String> loadedRejectStates = new ArrayList<>();
        String loadedStartState = null;

        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i).trim();

            if (line.isEmpty()) {
                continue;
            }

            if (line.contains("->")) {
                //Това е преход
                String clean = line.replace("->", ",").replace(" ", "");
                String[] p = clean.split(",");

                if (p.length == 5) {
                    loadedTransitions.add(
                            new Transition(
                                    p[0],
                                    p[1].charAt(0),
                                    p[2],
                                    p[3].charAt(0),
                                    p[4].charAt(0)
                            )
                    );
                }
            } else if (line.startsWith("Start:")) {
                //Това е началното състояние
                loadedStartState = line.replace("Start:", "").trim();

                if (!loadedStartState.isEmpty()) {
                    tm.addState(loadedStartState);
                }
            } else if (line.startsWith("Accept:")) {
                //Това са приемащите състояния
                String acceptLine = line.replace("Accept:", "").trim();
                addStatesToList(acceptLine, loadedAcceptStates);
            } else if (line.startsWith("Reject:")) {
                //Това са отхвърлящите състояния
                String rejectLine = line.replace("Reject:", "").trim();
                addStatesToList(rejectLine, loadedRejectStates);
            } else {
                //Това са обикновените състояния
                String[] states = line.split(",");

                for (String s : states) {
                    if (!s.trim().isEmpty()) {
                        tm.addState(s.trim());
                    }
                }
            }
        }

        //Добавяме състоянията от преходите
        for (Transition t : loadedTransitions) {
            tm.addState(t.getFromState());
            tm.addState(t.getToState());
        }

        //Добавяме приемащите състояния
        for (String state : loadedAcceptStates) {
            tm.addAcceptState(state);
        }

        //Добавяме отхвърлящите състояния
        for (String state : loadedRejectStates) {
            tm.addRejectState(state);
        }

        //Задаваме началното състояние след като всички състояния вече са добавени
        if (loadedStartState != null && !loadedStartState.isEmpty()) {
            tm.setStartState(loadedStartState);
        }

        //След като състоянията вече съществуват, добавяме преходите
        for (Transition t : loadedTransitions) {
            tm.addTransition(t);
        }

        machines.put(id, tm);
        return id;
    }

    //Добавя състояния от ред Accept:/Reject: към временен списък
    private void addStatesToList(String line, List<String> statesList) {
        if (line == null || line.isBlank()) {
            return;
        }

        String[] states = line.split(",");

        for (String state : states) {
            if (!state.trim().isEmpty()) {
                statesList.add(state.trim());
            }
        }
    }

    //Зарежда всички машини от файл
    public void deserializeAll(List<String> lines) {
        if (lines == null || lines.isEmpty()) {
            return;
        }

        List<String> currentMachineData = new ArrayList<>();

        for (String line : lines) {
            String trimmed = line.trim();

            //Ако намерим разделителя, пращаме събраното за зареждане
            if (trimmed.equals("---")) {
                if (!currentMachineData.isEmpty()) {
                    deserializeAndAdd(currentMachineData);
                    currentMachineData.clear();
                }
            } else if (!trimmed.isEmpty()) {
                //Добавяме реда само ако не е разделител или празен
                currentMachineData.add(trimmed);
            }
        }

        //За последната машина, ако файлът не завършва с ---
        if (!currentMachineData.isEmpty()) {
            deserializeAndAdd(currentMachineData);
        }
    }
}