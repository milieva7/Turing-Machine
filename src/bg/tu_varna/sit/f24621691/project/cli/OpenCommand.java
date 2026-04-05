package bg.tu_varna.sit.f24621691.project.cli;

import bg.tu_varna.sit.f24621691.project.core.MachineManager;
import bg.tu_varna.sit.f24621691.project.cli.exceptions.ConfigurationException;
import bg.tu_varna.sit.f24621691.project.model.TuringMachine;
import bg.tu_varna.sit.f24621691.project.model.Transition;
import java.util.List;

public class OpenCommand implements ICommand {
    private final CommandLineInterface cli;
    private final MachineManager manager;

    public OpenCommand(CommandLineInterface cli, MachineManager manager) {
        this.cli = cli;
        this.manager = manager;
    }

    @Override
    public void execute(String[] args) {
        //Валидация на входа
        if (args.length < 2) {
            throw new IllegalArgumentException("Моля, посочете път до файл! open <path>");
        }

        String path = args[1];

        //Четем редовете.
        //Ако файлът го няма, той ще се създаде и тук ще получим празен списък
        List<String> lines = cli.getFileReader().read(path);

        manager.clear();
        TuringMachine currentTM = null;

        //Парсваме съдържанието (ако има такова)
        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty() || line.startsWith("---")) continue;

            if (line.startsWith("TM:")) {
                String id = line.replace("TM:", "").trim();
                currentTM = new TuringMachine(id);
                manager.addMachine(currentTM);
            } else if (line.startsWith("START:") && currentTM != null) {
                String startState = line.replace("START:", "").trim();
                currentTM.setStartState(startState);
            } else if (line.contains("->") && currentTM != null) {
                parseTransition(currentTM, line);
            }
        }

        //Всички защитени команди са вече достъпни
        cli.setCurrentFilePath(path);

        System.out.println("Успешно отворен файл: " + extractFileName(path));
        if (lines.isEmpty()) {
            System.out.println("Можете да започнете работа с 'newtm'.");
        }
    }

    private void parseTransition(TuringMachine tm, String line) {
        try {
            String[] parts = line.split("->");
            String[] left = parts[0].split(",");
            String[] right = parts[1].split(",");

            String q = left[0].trim();
            char read = left[1].trim().charAt(0);
            String q2 = right[0].trim();
            char write = right[1].trim().charAt(0);
            char move = right[2].trim().charAt(0);

            tm.addTransition(new Transition(q, read, q2, write, move));
        } catch (Exception e) {
            throw new ConfigurationException("Грешка при четене на преход в '" + tm.getId() + "': " + line);
        }
    }

    private String extractFileName(String path) {
        int lastIdx = Math.max(path.lastIndexOf('\\'), path.lastIndexOf('/'));
        return (lastIdx == -1) ? path : path.substring(lastIdx + 1);
    }
}