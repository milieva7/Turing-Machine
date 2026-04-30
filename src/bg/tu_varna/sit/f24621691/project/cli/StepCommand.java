package bg.tu_varna.sit.f24621691.project.cli;

import bg.tu_varna.sit.f24621691.project.core.MachineManager;
import bg.tu_varna.sit.f24621691.project.model.TuringMachine;
import bg.tu_varna.sit.f24621691.project.model.exceptions.MachineNotInitializedException;

public class StepCommand extends AbstractCommand {
    private final MachineManager manager;

    public StepCommand(MachineManager manager) {
        super("step <id>", "Изпълнява една стъпка от работата на дадена машина.");
        this.manager = manager;
    }

    @Override
    public void execute(String[] args) {
        //Проверка за правилен брой аргументи
        validateArgs(args, 2, 2);

        String id = args[1];

        //Търсим машината по ID.
        //Ако такава машина не съществува, manager-ът ще хвърли exception.
        TuringMachine tm = manager.getMachine(id);

        //Проверка дали машината е инициализирана с входна дума
        if (tm.getTape() == null) {
            throw new MachineNotInitializedException(
                    "Операцията не може да бъде изпълнена. Машина '" + id + "' не е инициализирана с входна дума."
            );
        }

        //Ако машината вече е спряла, не изпълняваме нова стъпка
        if (tm.isHalted()) {
            System.out.println("Машината е в крайно състояние и е прекратила работа.");
            System.out.println("Текущо състояние: " + tm.getCurrentState());
            return;
        }

        //Изпълняваме една стъпка от машината
        tm.step();

        //Показваме новото текущо състояние
        System.out.println("Успешно изпълнена стъпка.");
        System.out.println("Текущо оперативно състояние: " + tm.getCurrentState());
    }
}