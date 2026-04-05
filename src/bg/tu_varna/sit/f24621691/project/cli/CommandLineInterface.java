package bg.tu_varna.sit.f24621691.project.cli;

import bg.tu_varna.sit.f24621691.project.core.FileServiceReader;
import bg.tu_varna.sit.f24621691.project.io.FileServiceWriter;
import bg.tu_varna.sit.f24621691.project.core.MachineManager;
import bg.tu_varna.sit.f24621691.project.cli.exceptions.UnauthorizedCommandException;
import bg.tu_varna.sit.f24621691.project.cli.exceptions.UnknownCommandException;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CommandLineInterface {
    private final Map<Command, ICommand> commands = new HashMap<>();
    private final MachineManager manager = new MachineManager();
    private final FileServiceReader fileReader = new FileServiceReader();
    private final FileServiceWriter fileWriter = new FileServiceWriter();
    private String currentFilePath = null;
    private boolean isRunning = true;

    public CommandLineInterface() {
        //Регистрация на командите
        commands.put(Command.SAVE, new SaveCommand(this, manager));
        commands.put(Command.SAVEAS, new SaveAsCommand(this, manager));
        commands.put(Command.HELP, new HelpCommand());
        commands.put(Command.EXIT, new ExitCommand());
        commands.put(Command.OPEN, new OpenCommand(this, manager));
        commands.put(Command.CLOSE, new CloseCommand(this, manager));
        commands.put(Command.NEWTM, new NewTMCommand(manager));
        commands.put(Command.PRINT, new PrintCommand(manager));
        commands.put(Command.LIST, new ListCommand(manager));
        commands.put(Command.ADDTRANS, new AddTransCommand(manager));

    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("--- Симулатор на Тюринг Машина ---");
        System.out.println("Напишете 'help' за списък с команди.");

        while (isRunning) {
            System.out.print("> ");
            String input = scanner.nextLine();

            try {
                processInput(input);
            } catch (Exception e) {

                System.err.println("Грешка: " + e.getMessage());
            }
        }
        scanner.close();
    }

    private void processInput(String input) {
        if (input == null || input.isBlank()) return;

        String[] parts = input.trim().split("\\s+");
        Command cmdType = Command.fromString(parts[0]);

        //Проверка за непозната команда
        if (cmdType == Command.UNKNOWN) {
            throw new UnknownCommandException("Командата '" + parts[0] + "' не съществува. Напишете 'help'.");
        }

        //Проверка за EXIT
        if (cmdType == Command.EXIT) {
            stop();
            return;
        }

        //Проверка за достъп
        if (isProtectedCommand(cmdType) && currentFilePath == null) {
            throw new UnauthorizedCommandException("Първо трябва да отворите файл с командата 'open'!");
        }

        //Изпълнение
        ICommand command = commands.get(cmdType);
        if (command != null) {
            command.execute(parts);
        } else {
            System.out.println("Командата '" + cmdType + "' е разпозната, но още не е имплементирана.");
        }
    }

    private boolean isProtectedCommand(Command cmd) {
        // Само тези команди са достъпни без отворен файл.
        return cmd != Command.OPEN &&
                cmd != Command.HELP &&
                cmd != Command.EXIT &&
                cmd != Command.UNKNOWN;
    }

    public void stop() {
        this.isRunning = false;
        System.out.println("Излизане от програмата...");
    }

    //Гетери за командите
    public FileServiceReader getFileReader() {
        return fileReader;
    }
    public FileServiceWriter getFileWriter() {
        return fileWriter;
    }
    public String getCurrentFilePath() {
        return currentFilePath;
    }
    public void setCurrentFilePath(String path) {
        this.currentFilePath = path;
    }
}