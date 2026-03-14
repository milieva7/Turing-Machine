package bg.tu_varna.sit.f24621691.project.core;

import bg.tu_varna.sit.f24621691.project.model.TuringMachine;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/*
 Класът управлява всички заредени Машини на Тюринг в програмата.
 Съхранява ги в списък и позволява достъп до тях чрез уникално ID.
 */
public class MachineManager {
    // Тук пазим машините
    private Map<String, TuringMachine> machines;

    public MachineManager() {
        this.machines = new HashMap<>();
    }

    /*
      Добавя нова машина в списъка.
      Проверява дали вече не съществува машина със същото ID.
     */
    public void addMachine(TuringMachine machine) {
        if (machines.containsKey(machine.getId())) {
            throw new IllegalArgumentException("Вече има заредена машина с ID " + machine.getId());
        }
        machines.put(machine.getId(), machine);
    }

    /*
      Намира и връща машина по нейното ID.
      Ако не я намери, връща null.
     */
    public TuringMachine getMachine(String id) {
        return machines.get(id);
    }

    /*
      Премахва машина от списъка по ID.
     */
    public void removeMachine(String id) {
        if (!machines.containsKey(id)) {
            System.out.println("Машина с такова ID не е намерена.");
            return;
        }
        machines.remove(id);
    }

    /*
      Връща списък с всички налични ID-та на заредените машини.
      Използва се за командата 'list'.
     */
    public Set<String> getAllMachineIds() {
        return machines.keySet();
    }

    /*
      Проверява дали в момента има заредени машини.
     */
    public boolean hasMachines() {
        return !machines.isEmpty();
    }
}
