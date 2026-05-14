package bg.tu_varna.sit.f24621691.project.cli;

import bg.tu_varna.sit.f24621691.project.core.MachineManager;
import bg.tu_varna.sit.f24621691.project.model.TuringMachine;
import bg.tu_varna.sit.f24621691.project.model.exceptions.MachineNotInitializedException;

public class TraceCommand extends AbstractCommand {
    private final MachineManager manager;

    //Подразбиращ се лимит, ако потребителят не подаде max=<n>
    private static final int DEFAULT_MAX_STEPS = 1000;

    public TraceCommand(MachineManager manager) {
        super("trace <id> [max=<n>]", "Показва изпълнението на машината стъпка по стъпка.");
        this.manager = manager;
    }

    @Override
    public void execute(String[] args) {
        //Проверка за правилен брой аргументи.
        //Позволяваме: trace <id>; trace <id> max=<n>
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

        System.out.println("=== Trace на машина '" + id + "' ===");

        //Показваме началната конфигурация преди изпълнение
        printConfiguration(tm, 0);

        int stepCount = 0;

        //Изпълняваме машината стъпка по стъпка,
        //като след всяка стъпка показваме новата конфигурация.
        //Спираме и ако достигнем максималния брой стъпки.
        while (!tm.isHalted() && stepCount < maxSteps) {
            tm.step();
            stepCount++;
            printConfiguration(tm, stepCount);
        }

        //Ако машината още не е спряла, значи сме стигнали лимита
        if (!tm.isHalted()) {
            System.out.println("Достигнат е максималният брой стъпки: " + maxSteps);
            System.out.println("Trace е прекъснат, защото машината не е приключила изпълнението си.");
            System.out.println("===============================");
            return;
        }

        //Показваме крайния резултат
        if (tm.getAcceptStates().contains(tm.getCurrentState())) {
            System.out.println("Резултат: машината е приела думата.");
        } else if (tm.getRejectStates().contains(tm.getCurrentState())) {
            System.out.println("Резултат: машината е отхвърлила думата.");
        } else if (tm.isHaltedNoTransition()) {
            System.out.println("Резултат: машината е спряла поради липсващ преход.");
        } else {
            System.out.println("Резултат: машината е спряла.");
        }

        System.out.println("===============================");
    }

    //Показва текущата конфигурация на машината:
    //номер на стъпката, текущо състояние, съдържание на лентата и позиция на главата
    private void printConfiguration(TuringMachine tm, int stepNumber) {
        String content = tm.getTape().getContent();
        int headPosition = tm.getTape().getHeadPosition();

        System.out.println("Стъпка " + stepNumber + ":");
        System.out.println("Състояние: " + tm.getCurrentState());
        System.out.println(content);

        //Печатаме маркер под позицията на главата
        for (int i = 0; i < headPosition; i++) {
            System.out.print(" ");
        }

        System.out.println("^");
        System.out.println();
    }

    //Взима max=<n> от аргументите, ако е подаден
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