package bg.tu_varna.sit.f24621691.project.cli;

import bg.tu_varna.sit.f24621691.project.core.MachineManager;
import bg.tu_varna.sit.f24621691.project.cli.exceptions.ConfigurationException;
import bg.tu_varna.sit.f24621691.project.model.TuringMachine;

public class InitCommand extends AbstractCommand {
    private final MachineManager manager;

    public InitCommand(MachineManager manager) {
        super("init <id> <input>", "Инициализира машина с входна дума върху лентата.");
        this.manager = manager;
    }

    @Override
    public void execute(String[] args) {
        //Проверка за правилен брой аргументи
        validateArgs(args, 3, 3);

        String id = args[1];
        String input = args[2];

        //Проверка за празна входна дума
        if (input.isBlank()) {
            throw new ConfigurationException("Не можеш да пуснеш празна дума!");
        }

        //Търсим машината по ID.
        //Ако такава машина не съществува, manager-ът ще хвърли exception.
        TuringMachine tm = manager.getMachine(id);

        //Инициализираме машината с подадената дума
        tm.init(input);

        //Показваме резултата от инициализацията
        System.out.println("Машина '" + id + "' е инициализирана с дума: " + input);
        System.out.println("Текущо състояние: " + tm.getCurrentState());
    }
}