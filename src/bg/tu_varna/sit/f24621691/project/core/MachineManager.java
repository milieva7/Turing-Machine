package bg.tu_varna.sit.f24621691.project.core;

import bg.tu_varna.sit.f24621691.project.model.TuringMachine;
import bg.tu_varna.sit.f24621691.project.core.exceptions.*;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
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

    public List<String> getSerializableData() {
        List<String> data = new ArrayList<>();
        for (TuringMachine tm : machines.values()) {
            data.add("TM: " + tm.getId());
            for (bg.tu_varna.sit.f24621691.project.model.Transition t : tm.getTransitions()) {
                data.add(t.toString());
            }
        }
        return data;
    }

    public void clear() {
        this.machines.clear();
    }
}
