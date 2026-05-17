package bg.tu_varna.sit.f24621691.project.cli;

import bg.tu_varna.sit.f24621691.project.core.MachineManager;
import bg.tu_varna.sit.f24621691.project.model.TuringMachine;

/**
 * Команда за задаване на начално състояние на конкретна Машина на Тюринг.
 * Машината се намира по ID, след което се задава подаденото състояние като начално.
 */
public class SetStartCommand extends AbstractCommand {
    private final MachineManager manager;

    /**
     * Създава команда за задаване на начално състояние.
     *
     * @param manager мениджърът, от който се взима машината
     */
    public SetStartCommand(MachineManager manager) {
        super("setstart <id> <state>", "Задава начално състояние на дадена машина.");
        this.manager = manager;
    }

    /**
     * Изпълнява командата setstart.
     * Задава начално състояние на машина с подадено ID.
     *
     * @param args аргументи на командата
     */
    @Override
    public void execute(String[] args) {
        //Проверка за правилен брой аргументи
        validateArgs(args, 3, 3);

        String id = args[1];
        String state = args[2];

        //Търсим машината по ID.
        //Ако такава машина не съществува, manager-ът ще хвърли exception.
        TuringMachine tm = manager.getMachine(id);

        //Задаваме началното състояние.
        //Ако състоянието не съществува в машината, TuringMachine ще хвърли exception.
        tm.setStartState(state);

        System.out.println("Успешно зададено начално състояние '" + state + "' за машина '" + id + "'.");
    }
}