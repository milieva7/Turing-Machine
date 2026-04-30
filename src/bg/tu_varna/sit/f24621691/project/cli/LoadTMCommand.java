package bg.tu_varna.sit.f24621691.project.cli;

import bg.tu_varna.sit.f24621691.project.core.MachineManager;

import java.util.List;

public class LoadTMCommand extends AbstractCommand {
    private final CommandLineInterface cli;
    private final MachineManager manager;

    public LoadTMCommand(CommandLineInterface cli, MachineManager manager) {
        super("loadtm <path>", "Добавя машина или машини от външен файл към текущата сесия.");
        this.cli = cli;
        this.manager = manager;
    }

    @Override
    public void execute(String[] args) {
        //Проверка за правилен брой аргументи
        validateArgs(args, 2, 2);

        String filePath = args[1];

        //Четем всички редове от подадения файл
        List<String> lines = cli.getFileReader().read(filePath);

        //Ако файлът е празен, няма какво да зареждаме
        if (lines == null || lines.isEmpty()) {
            System.out.println("Файлът е празен. Няма машини за зареждане.");
            return;
        }

        //Подаваме съдържанието към manager-а,
        //който ще раздели машините по "---" и ще ги десериализира
        manager.deserializeAll(lines);

        System.out.println("Успешна операция: Процесът по зареждане приключи.");
    }
}