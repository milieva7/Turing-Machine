package bg.tu_varna.sit.f24621691.project.cli;

import java.io.IOException;

public class SaveAsCommand implements ICommand {
    private final CommandLineInterface cli;
    private final bg.tu_varna.sit.f24621691.project.core.MachineManager manager;

    public SaveAsCommand(CommandLineInterface cli, bg.tu_varna.sit.f24621691.project.core.MachineManager manager) {
        this.cli = cli;
        this.manager = manager;
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 2) {
            System.out.println("Грешка: Моля, посочете нов път за файла.");
            return;
        }

        String newPath = args[1];
        try {
            cli.getFileWriter().write(newPath, manager.getSerializableData());
            //Променяме текущия файл на новия
            cli.setCurrentFilePath(newPath);
            System.out.println("Successfully saved as " + newPath);
        } catch (IOException e) {
            System.out.println("Грешка при 'Save As': " + e.getMessage());
        }
    }
}