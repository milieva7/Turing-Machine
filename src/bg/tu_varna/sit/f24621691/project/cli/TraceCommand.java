package bg.tu_varna.sit.f24621691.project.cli;

import bg.tu_varna.sit.f24621691.project.core.MachineManager;
import bg.tu_varna.sit.f24621691.project.model.TuringMachine;
import bg.tu_varna.sit.f24621691.project.model.exceptions.MachineNotInitializedException;

public class TraceCommand extends AbstractCommand {
    private final MachineManager manager;

    public TraceCommand(MachineManager manager) {
        super("trace <id>", "Показва изпълнението на машината стъпка по стъпка.");
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

        System.out.println("=== Trace на машина '" + id + "' ===");

        //Показваме началната конфигурация преди изпълнение
        printConfiguration(tm, 0);

        int stepCount = 0;

        //Изпълняваме машината стъпка по стъпка,
        //като след всяка стъпка показваме новата конфигурация
        while (!tm.isHalted()) {
            tm.step();
            stepCount++;
            printConfiguration(tm, stepCount);
        }

        //Показваме крайния резултат
        if (tm.getAcceptStates().contains(tm.getCurrentState())) {
            System.out.println("Резултат: машината е приела думата.");
        } else if (tm.getRejectStates().contains(tm.getCurrentState())) {
            System.out.println("Резултат: машината е отхвърлила думата.");
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
}