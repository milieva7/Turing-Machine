package bg.tu_varna.sit.f24621691.project.cli;

public class CloseCommand implements ICommand {
    private final CommandLineInterface cli;
    private final bg.tu_varna.sit.f24621691.project.core.MachineManager manager;

    public CloseCommand(CommandLineInterface cli, bg.tu_varna.sit.f24621691.project.core.MachineManager manager) {
        this.cli = cli;
        this.manager = manager;
    }

    @Override
    public void execute(String[] args) {
        String path = cli.getCurrentFilePath();

        if (path == null) {
            System.out.println("В момента няма отворен файл, който да бъде затворен.");
            return;
        }

        //Извличаме името на файла за съобщението
        String fileName = path.contains("\\") ? path.substring(path.lastIndexOf('\\') + 1) : path;

        //Нулираме пътя в CLI (автоматично заключва защитените команди)
        cli.setCurrentFilePath(null);

        //Изчистваме машините от мениджъра
        manager.clear();

        System.out.println("Successfully closed " + fileName);
    }
}