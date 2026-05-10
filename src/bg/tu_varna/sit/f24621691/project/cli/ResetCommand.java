package bg.tu_varna.sit.f24621691.project.cli;

import bg.tu_varna.sit.f24621691.project.core.MachineManager;
import bg.tu_varna.sit.f24621691.project.model.TuringMachine;

public class ResetCommand extends AbstractCommand {
    private final MachineManager manager;

    public ResetCommand(MachineManager manager) {
        super("reset <id>", "Нулира текущото изпълнение на машината.");
        this.manager = manager;
    }

    @Override
    public void execute(String[] args) {
        //Проверка за правилен брой аргументи
        validateArgs(args, 2, 2);

        String id = args[1];

        //Търсим машината по ID.
        //Ако няма такава машина, manager-ът ще хвърли exception.
        TuringMachine tm = manager.getMachine(id);

        //Нулираме текущото изпълнение на машината
        tm.reset();

        System.out.println("Машина '" + id + "' беше успешно нулирана.");
        System.out.println("Необходимо е ново 'init', за да започне отново изпълнение.");
    }
}