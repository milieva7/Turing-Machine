package bg.tu_varna.sit.f24621691.project.cli;

import bg.tu_varna.sit.f24621691.project.core.MachineManager;
import bg.tu_varna.sit.f24621691.project.model.TuringMachine;
import bg.tu_varna.sit.f24621691.project.core.exceptions.MachineNotFoundException;
import bg.tu_varna.sit.f24621691.project.cli.exceptions.ConfigurationException;

public class SaveTMCommand implements ICommand {
    private final MachineManager manager;
    private final CommandLineInterface cli;

    public SaveTMCommand(CommandLineInterface cli, MachineManager manager) {
        this.cli = cli;
        this.manager = manager;
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 3) {
            System.out.println("Грешка: Невалидни параметри. Правилен формат: 'saveTM <id> <path>'");
            return;
        }

        String id = args[1];
        String destinationPath = args[2];

        try {
            TuringMachine tm = manager.getMachine(id);

            //Проверка за съществуване
            if (tm == null) {
                throw new MachineNotFoundException("Машина с идентификатор '" + id + "' не е открита в текущата сесия.");
            }

            //Изпълнение на записа
            //Тук викаме твоя файлов обект
            cli.getFileWriter().writeSingle(destinationPath, tm.toString());

            System.out.println("Успешна операция: Машина " + id + " беше експортирана в " + destinationPath);

        } catch (MachineNotFoundException e) {
            System.out.println("Грешка при експорт: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Системна грешка: Неуспешен запис на файл. Детайли: " + e.getMessage());
        }
    }
}