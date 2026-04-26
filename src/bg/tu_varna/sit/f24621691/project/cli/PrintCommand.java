package bg.tu_varna.sit.f24621691.project.cli;

import bg.tu_varna.sit.f24621691.project.core.MachineManager;
import bg.tu_varna.sit.f24621691.project.model.TuringMachine;
import bg.tu_varna.sit.f24621691.project.model.Transition;

public class PrintCommand implements ICommand {
    private final MachineManager manager;

    public PrintCommand(MachineManager manager) {
        this.manager = manager;
    }

    @Override
    public void execute(String[] args) {
        //Проверка за аргументи
        if (args.length < 2) {
            System.out.println("Грешка: Трябва да посочиш ID! Употреба: print <id>");
            return;
        }

        String id = args[1];
        TuringMachine tm = manager.getMachine(id);

        //Проверка дали машината съществува
        if (tm == null) {
            System.out.println("Грешка: Машина с ID '" + id + "' не е заредена.");
            return;
        }

        System.out.println("--------------------------------------");
        System.out.println(" МАШИНА ID: " + tm.getId());
        System.out.println(" Начално състояние: " + (tm.getStartState() != null ? tm.getStartState() : "---"));


        String statesList = (tm.getStates() != null && !tm.getStates().isEmpty())
                ? String.join(", ", tm.getStates())
                : "---";
        System.out.println(" Състояния: " + statesList);


        System.out.println("--------------------------------------");

        System.out.println("Преходи:");

        if (tm.getTransitions() == null || tm.getTransitions().isEmpty()) {
            System.out.println(" (Няма добавени преходи)");
        } else {
            for (Transition t : tm.getTransitions()) {
                System.out.print(" Ако си в [" + t.getFromState() + "] и видиш '" + t.getReadSymbol() + "'");
                System.out.print(" -> Отиди в [" + t.getToState() + "], напиши '" + t.getWriteSymbol() + "'");
                System.out.println(", посока: " + t.getDirection());
            }
        }
        System.out.println("--------------------------------------");
    }
}