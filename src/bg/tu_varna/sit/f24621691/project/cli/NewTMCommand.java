package bg.tu_varna.sit.f24621691.project.cli;

import bg.tu_varna.sit.f24621691.project.core.MachineManager;
import bg.tu_varna.sit.f24621691.project.model.TuringMachine;

/**
 * Команда за създаване на нова празна Машина на Тюринг.
 * Новата машина се добавя в MachineManager с подадено от потребителя ID.
 */
public class NewTMCommand extends AbstractCommand {
    private final MachineManager manager;

    /**
     * Създава команда за добавяне на нова машина.
     *
     * @param manager мениджърът, в който ще бъде добавена новата машина
     */
    public NewTMCommand(MachineManager manager) {
        super("newtm <id>", "Създава нова празна Тюринг машина.");
        this.manager = manager;
    }

    /**
     * Изпълнява командата newtm.
     * Създава нова Машина на Тюринг с подаденото ID.
     *
     * @param args аргументи на командата
     */
    @Override
    public void execute(String[] args) {
        //Проверка за правилен брой аргументи
        validateArgs(args, 2, 2);

        String id = args[1];

        //Създаваме нова машина с подаденото ID и я добавяме в manager-а.
        //Ако вече има машина със същото ID, manager-ът ще хвърли exception.
        manager.addMachine(new TuringMachine(id));

        // Ако не е хвърлен exception, значи машината е създадена успешно
        System.out.println("Успешно създадена нова машина с ID: " + id);
    }
}