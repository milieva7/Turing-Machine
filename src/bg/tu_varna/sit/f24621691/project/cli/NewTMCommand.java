package bg.tu_varna.sit.f24621691.project.cli;

import bg.tu_varna.sit.f24621691.project.core.MachineManager;
import bg.tu_varna.sit.f24621691.project.model.TuringMachine;

public class NewTMCommand extends AbstractCommand {
    private final MachineManager manager;

    public NewTMCommand(MachineManager manager) {
        super("newtm <id>", "Създава нова празна Тюринг машина.");
        this.manager = manager;
    }

    @Override
    public void execute(String[] args) {
        //Проверка за правилен брой аргументи
        validateArgs(args, 2, 2);

        String id = args[1];

        //Създаваме нова машина с подаденото ID и я добавяме в manager-а.
        //Ако вече има машина със същото ID, manager-ът ще хвърли exception.
        manager.addMachine(new TuringMachine(id));

        // Ако не е хвърлен exception, значи машината е създадена успешно
        System.out.println("Успешно създадена нова машина с ID: " + id);
    }
}