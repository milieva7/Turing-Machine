package bg.tu_varna.sit.f24621691.project.cli;

import bg.tu_varna.sit.f24621691.project.core.MachineManager;

public class SaveAsCommand implements ICommand {
    private final CommandLineInterface cli;
    private final MachineManager manager;

    // ТРЯБВА ТИ ТОЗИ КОНСТРУКТОР!
    // Той казва на командата кои са CLI и Manager.
    public SaveAsCommand(CommandLineInterface cli, MachineManager manager) {
        this.cli = cli;
        this.manager = manager;
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 2) {
            throw new IllegalArgumentException("Трябва да посочиш път до файл! (saveas <path>)");
        }

        String newPath = args[1];

        // Вече cli и manager са достъпни тук
        cli.getFileWriter().write(newPath, manager.getSerializableData());

        cli.setCurrentFilePath(newPath);
        System.out.println("Успешно записано като: " + newPath);
    }
}