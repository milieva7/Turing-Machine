package bg.tu_varna.sit.f24621691.project.cli;

import bg.tu_varna.sit.f24621691.project.cli.exceptions.UnauthorizedCommandException;
import bg.tu_varna.sit.f24621691.project.cli.exceptions.UnknownCommandException;
import bg.tu_varna.sit.f24621691.project.core.MachineManager;
import bg.tu_varna.sit.f24621691.project.io.FileServiceReader;
import bg.tu_varna.sit.f24621691.project.io.FileServiceWriter;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Класът реализира командния интерфейс на приложението.
 * Отговаря за регистриране на командите, четене на вход от конзолата
 * и извикване на съответната команда според въведения текст.
 */
public class CommandLineInterface {
    private final Map<Command, ICommand> commands = new HashMap<>();
    private final MachineManager manager = new MachineManager();
    private final FileServiceReader fileReader = new FileServiceReader();
    private final FileServiceWriter fileWriter = new FileServiceWriter();

    private String currentFilePath = null;
    private boolean isRunning = true;

    /**
     * Създава нов команден интерфейс и регистрира всички налични команди.
     */
    public CommandLineInterface() {
        //Регистрация на командите
        commands.put(Command.SAVE, new SaveCommand(this, manager));
        commands.put(Command.SAVEAS, new SaveAsCommand(this, manager));
        commands.put(Command.HELP, new HelpCommand());
        commands.put(Command.EXIT, new ExitCommand(this));
        commands.put(Command.OPEN, new OpenCommand(this, manager));
        commands.put(Command.CLOSE, new CloseCommand(this, manager));

        commands.put(Command.LIST, new ListCommand(manager));
        commands.put(Command.PRINT, new PrintCommand(manager));
        commands.put(Command.SAVETM, new SaveTMCommand(this, manager));
        commands.put(Command.LOADTM, new LoadTMCommand(this, manager));
        commands.put(Command.NEWTM, new NewTMCommand(manager));

        commands.put(Command.ADDSTATE, new AddStateCommand(manager));
        commands.put(Command.SETSTART, new SetStartCommand(manager));
        commands.put(Command.ADDACCEPT, new AddAcceptCommand(manager));
        commands.put(Command.ADDREJECT, new AddRejectCommand(manager));
        commands.put(Command.ADDTRANS, new AddTransCommand(manager));
        commands.put(Command.REMOVETRANS, new RemoveTransCommand(manager));
        commands.put(Command.CHECKDET, new CheckDetCommand(manager));

        commands.put(Command.INIT, new InitCommand(manager));
        commands.put(Command.STEP, new StepCommand(manager));
        commands.put(Command.RUN, new RunCommand(manager));
        commands.put(Command.STATUS, new StatusCommand(manager));
        commands.put(Command.TAPE, new TapeCommand(manager));
        commands.put(Command.RESET, new ResetCommand(manager));
        commands.put(Command.ACCEPTS, new AcceptsCommand(manager));
        commands.put(Command.TRACE, new TraceCommand(manager));
        commands.put(Command.REPORT, new ReportCommand(manager));
    }

    /**
     * Стартира главния цикъл на командния интерфейс.
     * Чете команди от конзолата, докато програмата не бъде спряна.
     */
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

    /**
     * Обработва въведена команда.
     * Разпознава командата, проверява дали е позволена за изпълнение
     * и извиква съответния команден обект.
     *
     * @param input текстът, въведен от потребителя
     * @throws UnknownCommandException ако командата не съществува
     * @throws UnauthorizedCommandException ако командата изисква отворен файл, но такъв няма
     */
    private void processInput(String input) {
        if (input == null || input.isBlank()) {
            return;
        }

        String[] parts = input.trim().split("\\s+");

        //Специална обработка за "save as <path>"
        //Превръщаме го вътрешно в "saveas <path>"
        if (parts.length >= 2
                && parts[0].equalsIgnoreCase("save")
                && parts[1].equalsIgnoreCase("as")) {

            if (parts.length < 3) {
                parts = new String[]{"saveas"};
            } else {
                String path = joinFrom(parts, 2);
                parts = new String[]{"saveas", path};
            }
        }

        Command cmdType = Command.fromString(parts[0]);

        //Проверка за непозната команда
        if (cmdType == Command.UNKNOWN) {
            throw new UnknownCommandException(
                    "Командата '" + parts[0] + "' не съществува. Напишете 'help'."
            );
        }

        //Всички команди освен open/help/exit изискват отворен файл
        if (isProtectedCommand(cmdType) && currentFilePath == null) {
            throw new UnauthorizedCommandException(
                    "Първо трябва да отворите файл с командата 'open'!"
            );
        }

        //Изпълнение на командата
        ICommand command = commands.get(cmdType);
        if (command != null) {
            command.execute(parts);
        } else {
            System.out.println("Командата '" + cmdType + "' е разпозната, но още не е имплементирана.");
        }
    }

    /**
     * Събира елементите от масив от даден индекс нататък в един текст.
     * Използва се при команди, които могат да съдържат път с интервали.
     *
     * @param parts масивът с аргументи
     * @param startIndex индексът, от който започва събирането
     * @return обединеният текст
     */
    private String joinFrom(String[] parts, int startIndex) {
        StringBuilder sb = new StringBuilder();

        for (int i = startIndex; i < parts.length; i++) {
            if (i > startIndex) {
                sb.append(" ");
            }
            sb.append(parts[i]);
        }

        return sb.toString();
    }

    /**
     * Проверява дали дадена команда изисква вече отворен файл.
     *
     * @param cmd командата, която се проверява
     * @return true ако командата е защитена, иначе false
     */
    private boolean isProtectedCommand(Command cmd) {
        return cmd != Command.OPEN &&
                cmd != Command.HELP &&
                cmd != Command.EXIT &&
                cmd != Command.UNKNOWN;
    }

    /**
     * Спира главния цикъл на командния интерфейс.
     */
    public void stop() {
        this.isRunning = false;
        System.out.println("Излизане от програмата...");
    }

    /**
     * Връща обекта за четене от файл.
     *
     * @return FileServiceReader обектът
     */
    public FileServiceReader getFileReader() {
        return fileReader;
    }

    /**
     * Връща обекта за запис във файл.
     *
     * @return FileServiceWriter обектът
     */
    public FileServiceWriter getFileWriter() {
        return fileWriter;
    }

    /**
     * Връща пътя към текущо отворения файл.
     *
     * @return текущият път до файл или null, ако няма отворен файл
     */
    public String getCurrentFilePath() {
        return currentFilePath;
    }

    /**
     * Задава пътя към текущо отворения файл.
     *
     * @param path новият път до текущия файл
     */
    public void setCurrentFilePath(String path) {
        this.currentFilePath = path;
    }
}