package bg.tu_varna.sit.f24621691.project.cli;

import bg.tu_varna.sit.f24621691.project.core.MachineManager;
import bg.tu_varna.sit.f24621691.project.model.TuringMachine;
import bg.tu_varna.sit.f24621691.project.model.Transition;

/**
 * Команда за извеждане на информация за конкретна Машина на Тюринг.
 * Показва ID, начално състояние, списък със състояния и всички преходи.
 */
public class PrintCommand extends AbstractCommand {
    private final MachineManager manager;

    /**
     * Създава команда за показване на информация за машина.
     *
     * @param manager мениджърът, от който се взима търсената машина
     */
    public PrintCommand(MachineManager manager) {
        super("print <id>", "Извежда информация за машина и нейните преходи.");
        this.manager = manager;
    }

    /**
     * Изпълнява командата print.
     * Намира машина по ID и извежда информация за нея в конзолата.
     *
     * @param args аргументи на командата
     */
    @Override
    public void execute(String[] args) {
        //Проверка за правилен брой аргументи
        validateArgs(args, 2, 2);

        String id = args[1];

        //Търсим машината по ID.
        //Ако такава машина не съществува, manager-ът ще хвърли exception.
        TuringMachine tm = manager.getMachine(id);

        System.out.println("--------------------------------------");
        System.out.println(" МАШИНА ID: " + tm.getId());
        System.out.println(" Начално състояние: " +
                (tm.getStartState() != null ? tm.getStartState() : "---"));

        //Подготвяме списък със състоянията за печат
        String statesList = !tm.getStates().isEmpty()
                ? String.join(", ", tm.getStates())
                : "---";
        System.out.println(" Състояния: " + statesList);

        System.out.println("--------------------------------------");
        System.out.println("Преходи:");

        //Ако няма преходи, показваме съобщение
        if (tm.getTransitions().isEmpty()) {
            System.out.println(" (Няма добавени преходи)");
        } else {
            //Извеждаме всеки преход в по-четим формат
            for (Transition t : tm.getTransitions()) {
                System.out.print(" Ако си в [" + t.getFromState() + "] и видиш '" + t.getReadSymbol() + "'");
                System.out.print(" -> Отиди в [" + t.getToState() + "], напиши '" + t.getWriteSymbol() + "'");
                System.out.println(", посока: " + t.getDirection());
            }
        }

        System.out.println("--------------------------------------");
    }
}