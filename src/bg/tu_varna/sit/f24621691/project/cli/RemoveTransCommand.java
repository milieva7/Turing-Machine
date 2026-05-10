package bg.tu_varna.sit.f24621691.project.cli;

import bg.tu_varna.sit.f24621691.project.core.MachineManager;
import bg.tu_varna.sit.f24621691.project.model.TuringMachine;

public class RemoveTransCommand extends AbstractCommand {
    private final MachineManager manager;

    public RemoveTransCommand(MachineManager manager) {
        super("removetrans <id> <state> <read>", "Премахва преход по дадено състояние и символ.");
        this.manager = manager;
    }

    @Override
    public void execute(String[] args) {
        //Проверка за правилен брой аргументи
        validateArgs(args, 4, 4);

        String id = args[1];
        String state = args[2];
        char readSymbol = args[3].charAt(0);

        //Търсим машината по ID.
        //Ако няма такава машина, manager-ът ще хвърли exception.
        TuringMachine tm = manager.getMachine(id);

        //Премахваме прехода за подаденото състояние и символ
        tm.removeTransition(state, readSymbol);

        System.out.println("Успешно премахнат преход от състояние '" + state +
                "' със символ '" + readSymbol + "' за машина '" + id + "'.");
    }
}