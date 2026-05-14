package bg.tu_varna.sit.f24621691.project.cli;

import bg.tu_varna.sit.f24621691.project.core.MachineManager;

import java.util.List;

public class SaveTMCommand extends AbstractCommand {
    private final MachineManager manager;
    private final CommandLineInterface cli;

    public SaveTMCommand(CommandLineInterface cli, MachineManager manager) {
        super("savetm <id> <path>", "Експортира конкретна машина в отделен файл.");
        this.cli = cli;
        this.manager = manager;
    }

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