package bg.tu_varna.sit.f24621691.project.cli;

import bg.tu_varna.sit.f24621691.project.core.MachineManager;
import bg.tu_varna.sit.f24621691.project.model.TuringMachine;
import bg.tu_varna.sit.f24621691.project.model.exceptions.MachineNotInitializedException;

/**
 * Команда за изпълнение на една стъпка от работата на Машина на Тюринг.
 * Командата работи само ако машината вече е инициализирана с входна дума.
 */
public class StepCommand extends AbstractCommand {
    private final MachineManager manager;

    /**
     * Създава команда за изпълнение на една стъпка.
     *
     * @param manager мениджърът, от който се взима машината
     */
    public StepCommand(MachineManager manager) {
        super("step <id>", "Изпълнява една стъпка от работата на дадена машина.");
        this.manager = manager;
    }

    /**
     * Изпълнява командата step.
     * Намира машина по ID и изпълнява една стъпка от нейната работа.
     *
     * @param args аргументи на командата
     * @throws MachineNotInitializedException ако машината не е инициализирана
     */
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