package bg.tu_varna.sit.f24621691.project.cli;

import bg.tu_varna.sit.f24621691.project.core.MachineManager;
import bg.tu_varna.sit.f24621691.project.model.TuringMachine;

public class NewTMCommand implements ICommand {
    private final MachineManager manager;

    public NewTMCommand(MachineManager manager) {
        this.manager = manager;
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 2) {
            System.out.println("Моля, посочете име за новата машина.");
            return;
        }
        String id = args[1];
        manager.addMachine(new TuringMachine(id));
        System.out.println("Машина с ID '" + id + "' беше създадена успешно.");
    }
}