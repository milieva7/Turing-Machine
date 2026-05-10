package bg.tu_varna.sit.f24621691.project.cli;

import bg.tu_varna.sit.f24621691.project.core.MachineManager;
import bg.tu_varna.sit.f24621691.project.model.TuringMachine;
import bg.tu_varna.sit.f24621691.project.model.exceptions.MachineNotInitializedException;

public class RunCommand extends AbstractCommand {
    private final MachineManager manager;

    public RunCommand(MachineManager manager) {
        super("run <id>", "Изпълнява машината до достигане на крайно състояние.");
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

        //Проверка дали машината е инициализирана с входна дума
        if (tm.getTape() == null) {
            throw new MachineNotInitializedException(
                    "Машина '" + id + "' не е инициализирана с входна дума."
            );
        }

        //Ако машината вече е спряла, няма нужда от ново изпълнение
        if (tm.isHalted()) {
            System.out.println("Машината вече е в крайно състояние.");
            System.out.println("Текущо състояние: " + tm.getCurrentState());
            return;
        }

        int steps = 0;

        //Изпълняваме машината, докато не спре
        while (!tm.isHalted()) {
            tm.step();
            steps++;
        }

        System.out.println("Изпълнението приключи.");
        System.out.println("Изпълнени стъпки: " + steps);
        System.out.println("Крайно състояние: " + tm.getCurrentState());

        //Показваме резултата от изпълнението
        if (tm.getAcceptStates().contains(tm.getCurrentState())) {
            System.out.println("Резултат: машината е приела думата.");
        } else if (tm.getRejectStates().contains(tm.getCurrentState())) {
            System.out.println("Резултат: машината е отхвърлила думата.");
        } else if (tm.isHaltedNoTransition()) {
            System.out.println("Резултат: машината е спряла поради липсващ преход.");
        } else {
            System.out.println("Резултат: машината е спряла.");
        }
    }
}