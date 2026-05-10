package bg.tu_varna.sit.f24621691.project.cli;

import bg.tu_varna.sit.f24621691.project.core.MachineManager;
import bg.tu_varna.sit.f24621691.project.model.TuringMachine;

public class AddAcceptCommand extends AbstractCommand {
    private final MachineManager manager;

    public AddAcceptCommand(MachineManager manager) {
        super("addaccept <id> <state>", "Добавя приемащо състояние към дадена машина.");
        this.manager = manager;
    }

    @Override
    public void execute(String[] args) {
        //Проверка за правилен брой аргументи
        validateArgs(args, 3, 3);

        String id = args[1];
        String state = args[2];

        //Търсим машината по ID.
        //Ако няма такава машина, manager-ът ще хвърли exception.
        TuringMachine tm = manager.getMachine(id);

        //Добавяме приемащо състояние.
        //Ако състоянието още не съществува, то ще бъде добавено и в множеството от състояния.
        tm.addAcceptState(state);

        System.out.println("Успешно добавено приемащо състояние '" + state + "' към машина '" + id + "'.");
    }
}