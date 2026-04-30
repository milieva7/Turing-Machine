package bg.tu_varna.sit.f24621691.project.cli;

import bg.tu_varna.sit.f24621691.project.core.MachineManager;
import bg.tu_varna.sit.f24621691.project.model.TuringMachine;

public class SaveTMCommand extends AbstractCommand {
    private final MachineManager manager;
    private final CommandLineInterface cli;

    public SaveTMCommand(CommandLineInterface cli, MachineManager manager) {
        super("savetm <id> <path>", "Експортира конкретна машина в отделен файл.");
        this.cli = cli;
        this.manager = manager;
    }

    @Override
    public void execute(String[] args) {
        //Проверка за правилен брой аргументи
        validateArgs(args, 3, 3);

        String id = args[1];
        String destinationPath = args[2];

        //Търсим машината по ID.
        //Ако такава машина не съществува, manager-ът ще хвърли exception.
        TuringMachine tm = manager.getMachine(id);

        //Подготвяме машината във формат, подходящ за запис във файл
        String machineData = buildMachineData(tm);

        //Записваме машината в отделен файл.
        //writeSingle() ще добави и разделителя "---".
        cli.getFileWriter().writeSingle(destinationPath, machineData);

        System.out.println("Успешна операция: Машина '" + id + "' беше експортирана в " + destinationPath);
    }

    //Подготвя една машина във формат, съвместим със save/load логиката
    private String buildMachineData(TuringMachine tm) {
        StringBuilder sb = new StringBuilder();

        //Записваме ID-то на машината
        sb.append("TM: ").append(tm.getId()).append(System.lineSeparator());

        //Записваме всички преходи
        for (var t : tm.getTransitions()) {
            sb.append(t.toString()).append(System.lineSeparator());
        }

        //Записваме всички състояния на един ред, ако има такива
        if (!tm.getStates().isEmpty()) {
            sb.append(String.join(",", tm.getStates())).append(System.lineSeparator());
        }

        //Записваме началното състояние, ако е зададено
        if (tm.getStartState() != null) {
            sb.append("Start: ").append(tm.getStartState()).append(System.lineSeparator());
        }

        return sb.toString().trim();
    }
}