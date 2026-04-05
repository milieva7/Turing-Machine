package bg.tu_varna.sit.f24621691.project.cli;

import bg.tu_varna.sit.f24621691.project.core.MachineManager;
import bg.tu_varna.sit.f24621691.project.io.exceptions.TuringFileNotFoundException;

public class SaveCommand implements ICommand {
    private final CommandLineInterface cli;
    private final MachineManager manager;

    public SaveCommand(CommandLineInterface cli, MachineManager manager) {
        this.cli = cli;
        this.manager = manager;
    }

    @Override
    public void execute(String[] args) {
        String path = cli.getCurrentFilePath();

        //Дали имаме път
        if (path == null || path.isEmpty()) {
            throw new TuringFileNotFoundException("Няма отворен файл за запис! Използвай 'saveas' или първо 'open'.");
        }

        cli.getFileWriter().write(path, manager.getSerializableData());

        System.out.println("Успешно запазване в: " + extractFileName(path));
    }

    private String extractFileName(String path) {
        if (path == null) return "unknown";
        int lastIdx = Math.max(path.lastIndexOf('\\'), path.lastIndexOf('/'));
        return (lastIdx != -1) ? path.substring(lastIdx + 1) : path;
    }
}