package bg.tu_varna.sit.f24621691.project.cli;

import bg.tu_varna.sit.f24621691.project.core.MachineManager;
import bg.tu_varna.sit.f24621691.project.model.TuringMachine;

/**
 * Команда за добавяне на ново състояние към конкретна Машина на Тюринг.
 * Машината се намира по ID, след което към нея се добавя подаденото състояние.
 */
public class AddStateCommand extends AbstractCommand {
    private final MachineManager manager;

    /**
     * Създава команда за добавяне на състояние.
     *
     * @param manager мениджърът, от който се взима машината
     */
    public AddStateCommand(MachineManager manager) {
        super("addstate <machine_id> <state_name>", "Добавя ново състояние към дадена машина.");
        this.manager = manager;
    }

    /**
     * Изпълнява командата addstate.
     * Добавя ново състояние към машина с подадено ID.
     *
     * @param args аргументи на командата
     */
    @Override
    public void execute(String[] args) {
        //Проверка за правилен брой аргументи
        validateArgs(args, 3, 3);

        String id = args[1];
        String stateName = args[2];

        //Търсим машината по ID.
        //Ако такава машина не съществува, manager-ът ще хвърли exception.
        TuringMachine tm = manager.getMachine(id);

        //Добавяме новото състояние
        tm.addState(stateName);

        //Показваме резултата от операцията
        System.out.println("Успешно добавено състояние '" + stateName + "' към машина '" + id + "'.");
        System.out.println("Текущи състояния: " + tm.getStates());
    }
}