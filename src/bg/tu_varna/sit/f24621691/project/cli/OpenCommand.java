package bg.tu_varna.sit.f24621691.project.cli;

import bg.tu_varna.sit.f24621691.project.model.Transition;
import bg.tu_varna.sit.f24621691.project.model.TuringMachine;
import bg.tu_varna.sit.f24621691.project.core.MachineManager;
import java.io.IOException;
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
        if (args.length < 2) {
            System.out.println("Грешка: Посочете файл.");
            return;
        }

        String path = args[1];
        try {
            List<String> lines = cli.getFileReader().read(path);
            manager.clear();

            TuringMachine currentTM = null;

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

                    // Парсваме прехода q0, a -> q1, b, R
                    parseTransition(currentTM, line);
                }
            }

            cli.setCurrentFilePath(path);
            System.out.println("Успешно отваряне на" + path);

        } catch (Exception e) {
            System.out.println("Грешка при зареждане: " + e.getMessage());
        }
    }

    //Метод за разглобяване на прехода
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
            System.out.println("Прескочен невалиден преход: " + line);
        }
    }



    //Метод за извличане само на името на файла от пълния път
    private String extractFileName(String path) {
        int lastBack = path.lastIndexOf('\\');
        int lastForward = path.lastIndexOf('/');
        int index = Math.max(lastBack, lastForward);
        return (index == -1) ? path : path.substring(index + 1);
    }
}