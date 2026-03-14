package bg.tu_varna.sit.f24621691.project.cli;

import bg.tu_varna.sit.f24621691.project.core.MachineManager;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CommandLineInterface {
    private final Scanner scanner;
    private final Map<Command, ICommand> commands;

    public CommandLineInterface(MachineManager manager) {
        this.scanner = new Scanner(System.in);
        this.commands = new HashMap<>();

        // Регистрираме командите
        commands.put(Command.HELP, new HelpCommand());
        commands.put(Command.LIST, new ListCommand(manager));
        commands.put(Command.EXIT, new ExitCommand());
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
        ICommand command = commands.get(cmdType);

        if (command != null) {
            command.execute(parts); // Изпълняваме конкретния клас
        } else {
            System.out.println("Неизвестна команда. Напишете 'help'.");
        }
    }
}