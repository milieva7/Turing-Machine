package bg.tu_varna.sit.f24621691.project.cli;

import bg.tu_varna.sit.f24621691.project.core.MachineManager;

public class ListCommand implements ICommand {
    private final MachineManager manager;

    public ListCommand(MachineManager manager) {
        this.manager = manager;
    }

    @Override
    public void execute(String[] args) {
        if (!manager.hasMachines()) {
            System.out.println("Няма заредени машини.");
        } else {
            System.out.println("Заредени машини (ID): " + manager.getAllMachineIds());
        }
    }
}