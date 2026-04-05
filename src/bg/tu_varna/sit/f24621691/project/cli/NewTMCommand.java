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
            throw new IllegalArgumentException("Грешка: Липсва име! Напиши: newtm <id>");
        }

        String id = args[1];

        manager.addMachine(new TuringMachine(id));

        // 3. Ако горният ред не хвърли ексепшън, значи всичко е точно
        System.out.println("Успешно създадена нова машина с ID: " + id);
    }
}