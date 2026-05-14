package bg.tu_varna.sit.f24621691.project.cli;

import bg.tu_varna.sit.f24621691.project.core.MachineManager;
import bg.tu_varna.sit.f24621691.project.model.TuringMachine;
import bg.tu_varna.sit.f24621691.project.model.exceptions.MachineNotInitializedException;

public class RunCommand extends AbstractCommand {
    private final MachineManager manager;

    //Подразбиращ се лимит, ако потребителят не подаде max=<n>
    private static final int DEFAULT_MAX_STEPS = 1000;

    public RunCommand(MachineManager manager) {
        super("run <id> [max=<n>]", "Изпълнява машината до достигане на крайно състояние.");
        this.manager = manager;
    }

    @Override
    public void execute(String[] args) {
        //Проверка за правилен брой аргументи.
        //Позволяваме: run <id>; run <id> max=<n>
        validateArgs(args, 2, 3);

        String id = args[1];

        //Взимаме максималния брой стъпки.
        //Ако няма подаден max=<n>, използваме DEFAULT_MAX_STEPS.
        int maxSteps = getMaxSteps(args);

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

        //Изпълняваме машината, докато не спре или докато не стигнем лимита
        while (!tm.isHalted() && steps < maxSteps) {
            tm.step();
            steps++;
        }

        //Ако машината още не е спряла, значи сме стигнали max лимита
        if (!tm.isHalted()) {
            System.out.println("Достигнат е максималният брой стъпки: " + maxSteps);
            System.out.println("Машината не е приключила изпълнението си.");
            System.out.println("Текущо състояние: " + tm.getCurrentState());
            return;
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

    // Взима max=<n> от аргументите, ако е подаден
    private int getMaxSteps(String[] args) {
        //Ако няма трети аргумент, връщаме подразбиращия се лимит
        if (args.length < 3) {
            return DEFAULT_MAX_STEPS;
        }

        String maxArg = args[2];

        //Проверка дали аргументът е във формат max=<n>
        if (!maxArg.startsWith("max=")) {
            throw new IllegalArgumentException("Невалиден параметър. Използвай: max=<n>");
        }

        String value = maxArg.replace("max=", "");

        try {
            int maxSteps = Integer.parseInt(value);

            if (maxSteps <= 0) {
                throw new IllegalArgumentException("max трябва да бъде положително число.");
            }

            return maxSteps;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("max трябва да бъде число.");
        }
    }
}