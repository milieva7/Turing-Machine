package bg.tu_varna.sit.f24621691.project.cli;

import bg.tu_varna.sit.f24621691.project.core.MachineManager;
import bg.tu_varna.sit.f24621691.project.model.TuringMachine;

public class ReportCommand extends AbstractCommand {
    private final MachineManager manager;

    public ReportCommand(MachineManager manager) {
        super("report <id>", "Показва обобщен отчет за дадена машина.");
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

        System.out.println("========== ОТЧЕТ ЗА МАШИНА '" + id + "' ==========");

        //Основна информация за машината
        System.out.println("ID: " + tm.getId());
        System.out.println("Начално състояние: " +
                (tm.getStartState() != null ? tm.getStartState() : "---"));
        System.out.println("Брой състояния: " + tm.getStates().size());
        System.out.println("Брой преходи: " + tm.getTransitions().size());
        System.out.println("Приемащи състояния: " +
                (!tm.getAcceptStates().isEmpty() ? tm.getAcceptStates() : "---"));
        System.out.println("Отхвърлящи състояния: " +
                (!tm.getRejectStates().isEmpty() ? tm.getRejectStates() : "---"));

        //Ако машината не е инициализирана, отчетът приключва дотук
        if (tm.getTape() == null) {
            System.out.println("Изпълнение: машината не е инициализирана.");
            System.out.println("==================================================");
            return;
        }

        //Информация за текущото изпълнение
        System.out.println("Текущо състояние: " + tm.getCurrentState());
        System.out.println("Лента: " + tm.getTape().getContent());
        System.out.println("Позиция на главата: " + tm.getTape().getHeadPosition());

        //Проверка дали машината е спряла и какъв е резултатът
        if (tm.isHalted()) {
            System.out.println("Изпълнение: СПРЯЛА");

            if (tm.getAcceptStates().contains(tm.getCurrentState())) {
                System.out.println("Резултат: машината е приела думата.");
            } else if (tm.getRejectStates().contains(tm.getCurrentState())) {
                System.out.println("Резултат: машината е отхвърлила думата.");
            } else if (tm.isHaltedNoTransition()) {
                System.out.println("Резултат: машината е спряла поради липсващ преход.");
            } else {
                System.out.println("Резултат: машината е спряла.");
            }
        } else {
            System.out.println("Изпълнение: РАБОТИ");
        }

        System.out.println("==================================================");
    }
}