package bg.tu_varna.sit.f24621691.project.cli;

import bg.tu_varna.sit.f24621691.project.core.MachineManager;
import bg.tu_varna.sit.f24621691.project.model.TuringMachine;

public class SetStartCommand extends AbstractCommand {
    private final MachineManager manager;

    public SetStartCommand(MachineManager manager) {
        super("setstart <id> <state>", "Задава начално състояние на дадена машина.");
        this.manager = manager;
    }

    @Override
    public void execute(String[] args) {
        //Проверка за правилен брой аргументи
        validateArgs(args, 3, 3);

        String id = args[1];
        String state = args[2];

        //Търсим машината по ID.
        //Ако такава машина не съществува, manager-ът ще хвърли exception.
        TuringMachine tm = manager.getMachine(id);

        //Задаваме началното състояние.
        //Ако състоянието не съществува в машината, TuringMachine ще хвърли exception.
        tm.setStartState(state);

        System.out.println("Успешно зададено начално състояние '" + state + "' за машина '" + id + "'.");
    }
}