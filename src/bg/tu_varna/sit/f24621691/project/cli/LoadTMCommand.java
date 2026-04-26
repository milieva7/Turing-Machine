package bg.tu_varna.sit.f24621691.project.cli;

import bg.tu_varna.sit.f24621691.project.core.MachineManager;
import java.util.List;

public class LoadTMCommand implements ICommand {
    private final CommandLineInterface cli;
    private final MachineManager manager;

    public LoadTMCommand(CommandLineInterface cli, MachineManager manager) {
        this.cli = cli;
        this.manager = manager;
    }

    @Override
    public void execute(String[] args) {
        //Проверка дали потребителят е написал път до файла
        if (args.length < 2) {
            System.out.println("Грешка: Не е посочен файл!");
            System.out.println("Употреба: loadTM <път_до_файл>");
            return;
        }

        String filePath = args[1];

        try {
            //Четем всички редове от файла през FileService-а на CLI-то
            List<String> lines = cli.getFileReader().read(filePath);

            if (lines == null || lines.isEmpty()) {
                System.out.println("Грешка: Файлът е празен или не съществува.");
                return;
            }

            //Викаме десериализатора за целия файл
            manager.deserializeAll(lines);

            System.out.println("Успешна операция: Процесът по зареждане приключи.");

        } catch (Exception e) {
            System.out.println("Критична грешка при зареждане: " + e.getMessage());
        }
    }
}