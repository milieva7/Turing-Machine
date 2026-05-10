package bg.tu_varna.sit.f24621691.project.cli;

import bg.tu_varna.sit.f24621691.project.core.MachineManager;
import bg.tu_varna.sit.f24621691.project.model.TuringMachine;
import bg.tu_varna.sit.f24621691.project.model.exceptions.MachineNotInitializedException;

public class StatusCommand extends AbstractCommand {
    private final MachineManager manager;

    public StatusCommand(MachineManager manager) {
        super("status <id>", "Показва текущото състояние на изпълнение на дадена машина.");
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

        //Проверка дали машината е инициализирана
        if (tm.getTape() == null) {
            throw new MachineNotInitializedException(
                    "Машина '" + id + "' не е инициализирана с входна дума."
            );
        }

        System.out.println("=== Статус на машина '" + id + "' ===");
        System.out.println("Текущо състояние: " + tm.getCurrentState());
        System.out.println("Позиция на главата: " + tm.getTape().getHeadPosition());

        //Проверка дали машината е спряла
        if (tm.isHalted()) {
            System.out.println("Състояние на изпълнение: СПРЯЛА");

            if (tm.getAcceptStates().contains(tm.getCurrentState())) {
                System.out.println("Резултат: машината е приела думата.");
            } else if (tm.getRejectStates().contains(tm.getCurrentState())) {
                System.out.println("Резултат: машината е отхвърлила думата.");
            } else if (tm.isHaltedNoTransition()) {
                System.out.println("Резултат: машината е спряла поради липсващ преход.");
            }
        } else {
            System.out.println("Състояние на изпълнение: РАБОТИ");
        }

        System.out.println("===============================");
    }
}