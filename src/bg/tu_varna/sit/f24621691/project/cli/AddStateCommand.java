package bg.tu_varna.sit.f24621691.project.cli;

import bg.tu_varna.sit.f24621691.project.core.MachineManager;
import bg.tu_varna.sit.f24621691.project.model.TuringMachine;

public class AddStateCommand extends AbstractCommand {
    private final MachineManager manager;

    public AddStateCommand(MachineManager manager) {
        super("addstate <machine_id> <state_name>", "Добавя ново състояние към дадена машина.");
        this.manager = manager;
    }

    @Override
    public void execute(String[] args) {
        //Проверка за правилен брой аргументи
        validateArgs(args, 3, 3);

        String id = args[1];
        String stateName = args[2];

        //Търсим машината по ID.
        //Ако такава машина не съществува, manager-ът ще хвърли exception.
        TuringMachine tm = manager.getMachine(id);

        //Добавяме новото състояние
        tm.addState(stateName);

        //Показваме резултата от операцията
        System.out.println("Успешно добавено състояние '" + stateName + "' към машина '" + id + "'.");
        System.out.println("Текущи състояния: " + tm.getStates());
    }
}