package bg.tu_varna.sit.f24621691.project.cli;

import bg.tu_varna.sit.f24621691.project.core.MachineManager;

import java.util.List;

public class OpenCommand extends AbstractCommand {
    private final CommandLineInterface cli;
    private final MachineManager manager;

    public OpenCommand(CommandLineInterface cli, MachineManager manager) {
        super("open <path>", "Отваря файл и зарежда машините от него.");
        this.cli = cli;
        this.manager = manager;
    }

    @Override
    public void execute(String[] args) {
        //Проверка дали е подаден път до файл
        validateArgs(args, 2, 2);

        String path = args[1];

        //Четем редовете от файла.
        //Ако файлът не съществува, ще бъде създаден нов празен файл.
        List<String> lines = cli.getFileReader().read(path);

        //Изчистваме старите заредени машини
        manager.clear();

        //Ако файлът съдържа данни, ги подаваме към manager-а
        if (!lines.isEmpty()) {
            manager.deserializeAll(lines);
        }

        //Задаваме текущия активен файл
        cli.setCurrentFilePath(path);

        System.out.println("Успешно отворен файл: " + extractFileName(path));

        //Ако файлът е празен, подсказваме
        if (lines.isEmpty()) {
            System.out.println("Файлът е празен. Можете да започнете работа с 'newtm'.");
        }
    }

    //Извлича името на файла от пълния път
    private String extractFileName(String path) {
        int lastIdx = Math.max(path.lastIndexOf('\\'), path.lastIndexOf('/'));
        return (lastIdx == -1) ? path : path.substring(lastIdx + 1);
    }
}