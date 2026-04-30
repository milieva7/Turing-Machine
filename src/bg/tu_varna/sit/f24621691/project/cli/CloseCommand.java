package bg.tu_varna.sit.f24621691.project.cli;

import bg.tu_varna.sit.f24621691.project.core.MachineManager;

public class CloseCommand extends AbstractCommand {
    private final CommandLineInterface cli;
    private final MachineManager manager;

    public CloseCommand(CommandLineInterface cli, MachineManager manager) {
        super("close", "Затваря текущо отворения файл и изчиства заредените машини.");
        this.cli = cli;
        this.manager = manager;
    }

    @Override
    public void execute(String[] args) {
        //Проверка за правилен брой аргументи
        validateArgs(args, 1, 1);

        String path = cli.getCurrentFilePath();

        //Проверява за отворен файл
        if (path == null) {
            System.out.println("В момента няма отворен файл, който да бъде затворен.");
            return;
        }

        //Извличаме името на файла за съобщението
        String fileName = extractFileName(path);

        //Нулираме текущия път в CLI.
        //Така всички защитени команди автоматично стават недостъпни.
        cli.setCurrentFilePath(null);

        //Изчистваме всички заредени машини от manager-а
        manager.clear();

        System.out.println("Successfully closed " + fileName);
    }

    //Извлича името на файла от пълния път
    private String extractFileName(String path) {
        int lastIdx = Math.max(path.lastIndexOf('\\'), path.lastIndexOf('/'));
        return (lastIdx == -1) ? path : path.substring(lastIdx + 1);
    }
}