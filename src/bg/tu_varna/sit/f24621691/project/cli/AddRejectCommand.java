package bg.tu_varna.sit.f24621691.project.cli;

import bg.tu_varna.sit.f24621691.project.core.MachineManager;
import bg.tu_varna.sit.f24621691.project.model.TuringMachine;

/**
 * Команда за добавяне на отхвърлящо състояние към конкретна Машина на Тюринг.
 * При достигане на отхвърлящо състояние машината спира и отхвърля входната дума.
 */
public class AddRejectCommand extends AbstractCommand {
    private final MachineManager manager;

    /**
     * Създава команда за добавяне на отхвърлящо състояние.
     *
     * @param manager мениджърът, от който се взима машината
     */
    public AddRejectCommand(MachineManager manager) {
        super("addreject <id> <state>", "Добавя отхвърлящо състояние към дадена машина.");
        this.manager = manager;
    }

    /**
     * Изпълнява командата addreject.
     * Добавя отхвърлящо състояние към машина с подадено ID.
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
        //Ако няма такава машина, manager-ът ще хвърли exception.
        TuringMachine tm = manager.getMachine(id);

        //Добавяме отхвърлящо състояние.
        //Ако състоянието още не съществува, то ще бъде добавено и в множеството от състояния.
        tm.addRejectState(state);

        System.out.println("Успешно добавено отхвърлящо състояние '" + state + "' към машина '" + id + "'.");
    }
}