package bg.tu_varna.sit.f24621691.project.cli;

import bg.tu_varna.sit.f24621691.project.core.MachineManager;
import bg.tu_varna.sit.f24621691.project.model.TuringMachine;

/**
 * Команда за нулиране на текущото изпълнение на Машина на Тюринг.
 * Командата премахва текущата лента и текущото състояние,
 * но не изтрива състоянията, преходите и конфигурацията на машината.
 */
public class ResetCommand extends AbstractCommand {
    private final MachineManager manager;

    /**
     * Създава команда за нулиране на изпълнението.
     *
     * @param manager мениджърът, от който се взима машината
     */
    public ResetCommand(MachineManager manager) {
        super("reset <id>", "Нулира текущото изпълнение на машината.");
        this.manager = manager;
    }

    /**
     * Изпълнява командата reset.
     * Нулира текущото изпълнение на машина с подадено ID.
     *
     * @param args аргументи на командата
     */
    @Override
    public void execute(String[] args) {
        //Проверка за правилен брой аргументи
        validateArgs(args, 2, 2);

        String id = args[1];

        //Търсим машината по ID.
        //Ако няма такава машина, manager-ът ще хвърли exception.
        TuringMachine tm = manager.getMachine(id);

        //Нулираме текущото изпълнение на машината
        tm.reset();

        System.out.println("Машина '" + id + "' беше успешно нулирана.");
        System.out.println("Необходимо е ново 'init', за да започне отново изпълнение.");
    }
}