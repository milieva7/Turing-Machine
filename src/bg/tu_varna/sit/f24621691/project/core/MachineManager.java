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

    //Премахва машина от списъка по ID.
    public void removeMachine(String id) {
        if (!machines.containsKey(id)) {
            throw new MachineNotFoundException("Машина с ID '" + id + "' не е намерена.");
        }

        machines.remove(id);
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
            data.add("TM: " + tm.getId());

            for (Transition t : tm.getTransitions()) {
                data.add(t.toString());
            }

            if (!tm.getStates().isEmpty()) {
                data.add(String.join(",", tm.getStates()));
            }

            if (tm.getStartState() != null) {
                data.add("Start: " + tm.getStartState());
            }

            data.add("---");
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
                String startState = line.replace("Start:", "").trim();

                if (!startState.isEmpty()) {
                    tm.addState(startState);
                    tm.setStartState(startState);
                }
            } else {
                //Това са състояния
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

        //След като състоянията вече съществуват, добавяме преходите
        for (Transition t : loadedTransitions) {
            tm.addTransition(t);
        }

        machines.put(id, tm);
        return id;
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