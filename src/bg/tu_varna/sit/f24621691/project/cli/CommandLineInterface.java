package bg.tu_varna.sit.f24621691.project.cli;

import bg.tu_varna.sit.f24621691.project.core.MachineManager;
import bg.tu_varna.sit.f24621691.project.io.IReader;
import bg.tu_varna.sit.f24621691.project.io.IWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CommandLineInterface {
    private final Scanner scanner;
    private final Map<Command, ICommand> commands;
    private final MachineManager manager;
    private final IReader fileReader;
    private final IWriter fileWriter;
    private String currentFilePath = null;

    public CommandLineInterface(MachineManager manager, IReader reader, IWriter writer) {
        this.manager = manager;
        this.fileReader = reader;
        this.fileWriter = writer;
        this.scanner = new Scanner(System.in);
        this.commands = new HashMap<>();

        //Регистрираме командите
        commands.put(Command.HELP, new HelpCommand());
        commands.put(Command.LIST, new ListCommand(manager));
        commands.put(Command.EXIT, new ExitCommand());
        commands.put(Command.OPEN, new OpenCommand(this));
        commands.put(Command.CLOSE, new CloseCommand(this, manager));
        commands.put(Command.SAVE, new SaveCommand(this, manager));
        commands.put(Command.SAVEAS, new SaveAsCommand(this, manager));
    }

    public void start() {
        System.out.println("DTM Simulator Engine");
        System.out.println("Системата е готова. Напишете 'help', за да видите наличните команди.");

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) continue;

            processInput(input);
        }
    }

    private void processInput(String input) {
        String[] parts = input.split("\\s+");
        Command cmdType = Command.fromString(parts[0]);

        //Инженерна проверка за заключени команди
        if (currentFilePath == null && isProtectedCommand(cmdType)) {
            System.out.println("Грешка: Първо трябва да отворите файл с командата 'open'!");
            return;
        }

        ICommand command = commands.get(cmdType);
        if (command != null) {
            command.execute(parts);
        } else {
            System.out.println("Неизвестна команда. Напишете 'help'.");
        }
    }

    //Помощен метод за проверка на защитени команди
    private boolean isProtectedCommand(Command cmd) {
        return cmd != Command.OPEN && cmd != Command.HELP && cmd != Command.EXIT && cmd != Command.UNKNOWN;
    }

    //Гетъри и сетъри за управление на състоянието от командите
    public void setCurrentFilePath(String path) { this.currentFilePath = path; }
    public String getCurrentFilePath() { return currentFilePath; }
    public IReader getFileReader() { return fileReader; }
    public IWriter getFileWriter() { return fileWriter; }
}