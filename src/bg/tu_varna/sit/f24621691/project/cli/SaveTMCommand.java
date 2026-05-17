package bg.tu_varna.sit.f24621691.project.cli;

import bg.tu_varna.sit.f24621691.project.core.MachineManager;

import java.util.List;

/**
 * Команда за експортиране на конкретна Машина на Тюринг в отделен файл.
 * Машината се намира по ID и се записва във файлов формат, съвместим със save/load логиката.
 */
public class SaveTMCommand extends AbstractCommand {
    private final MachineManager manager;
    private final CommandLineInterface cli;

    /**
     * Създава команда за експортиране на конкретна машина.
     *
     * @param cli командният интерфейс, чрез който се достъпва FileServiceWriter
     * @param manager мениджърът, от който се взима машината за запис
     */
    public SaveTMCommand(CommandLineInterface cli, MachineManager manager) {
        super("savetm <id> <path>", "Експортира конкретна машина в отделен файл.");
        this.cli = cli;
        this.manager = manager;
    }

    /**
     * Изпълнява командата savetm.
     * Взима конкретна машина по ID и я записва в подадения файл.
     *
     * @param args аргументи на командата
     */
    @Override
    public void execute(String[] args) {
        //Проверка за правилен брой аргументи
        validateArgs(args, 3, 3);

        String id = args[1];
        String destinationPath = args[2];

        //Взимаме сериализираните данни за конкретната машина.
        //Ако машината не съществува, manager-ът ще хвърли exception.
        List<String> machineData = manager.getSerializableDataForMachine(id);

        //Записваме машината в отделен файл.
        //Използваме write(), защото machineData вече съдържа разделителя "---".
        cli.getFileWriter().write(destinationPath, machineData);

        System.out.println("Успешна операция: Машина '" + id + "' беше експортирана в " + destinationPath);
    }
}