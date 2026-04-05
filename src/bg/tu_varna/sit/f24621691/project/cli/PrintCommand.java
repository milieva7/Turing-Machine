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
        // 1. Валидация на аргументите
        if (args.length < 2) {
            throw new IllegalArgumentException("Трябва да посочиш ID! Напиши: print <id>");
        }

        String id = args[1];

        TuringMachine tm = manager.getMachine(id);

        System.out.println("--------------------------------------");
        System.out.println(" МАШИНА ID: " + tm.getId());
        System.out.println(" Начално състояние: " + (tm.getStartState() != null ? tm.getStartState() : "---"));
        System.out.println("--------------------------------------");

        System.out.println("Преходи:");

        if (tm.getTransitions().isEmpty()) {

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