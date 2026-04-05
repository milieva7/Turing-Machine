package bg.tu_varna.sit.f24621691.project.cli;

import bg.tu_varna.sit.f24621691.project.core.MachineManager;
import java.util.Set;

public class ListCommand implements ICommand {
    private final MachineManager manager;

    public ListCommand(MachineManager manager) {
        this.manager = manager;
    }

    @Override
    public void execute(String[] args) {

        if (!manager.hasMachines()) {
            System.out.println("В момента няма заредени машини в симулатора.");
            return;
        }

        Set<String> ids = manager.getAllMachineIds();

        System.out.println("=== Списък на заредените Тюринг машини ===");
        int count = 1;
        for (String id : ids) {
            System.out.println(count + ". ID: " + id);
            count++;
        }
        System.out.println("Общо: " + ids.size() + " машини.");
        System.out.println("==========================================");
    }
}