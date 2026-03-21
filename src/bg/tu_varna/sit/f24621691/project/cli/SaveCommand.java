package bg.tu_varna.sit.f24621691.project.cli;

import java.io.IOException;

public class SaveCommand implements ICommand {
    private final CommandLineInterface cli;
    private final bg.tu_varna.sit.f24621691.project.core.MachineManager manager;

    public SaveCommand(CommandLineInterface cli, bg.tu_varna.sit.f24621691.project.core.MachineManager manager) {
        this.cli = cli;
        this.manager = manager;
    }

    @Override
    public void execute(String[] args) {
        String path = cli.getCurrentFilePath();

        try {

            cli.getFileWriter().write(path, manager.getSerializableData());
            System.out.println("Successfully saved " + extractFileName(path));
        } catch (IOException e) {
            System.out.println("Грешка при запис: " + e.getMessage());
        }
    }

    private String extractFileName(String path) {
        return path.contains("\\") ? path.substring(path.lastIndexOf('\\') + 1) : path;
    }
}