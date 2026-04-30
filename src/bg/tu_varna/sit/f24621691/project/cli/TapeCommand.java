package bg.tu_varna.sit.f24621691.project.cli;

import bg.tu_varna.sit.f24621691.project.core.MachineManager;
import bg.tu_varna.sit.f24621691.project.model.TuringMachine;
import bg.tu_varna.sit.f24621691.project.model.exceptions.MachineNotInitializedException;

public class TapeCommand extends AbstractCommand {
    private final MachineManager manager;

    public TapeCommand(MachineManager manager) {
        super("tape <id>", "Показва съдържанието на лентата и позицията на главата.");
        this.manager = manager;
    }

    @Override
    public void execute(String[] args) {
        //Проверка за правилен брой аргументи
        validateArgs(args, 2, 2);

        String id = args[1];

        //Търсим машината по ID.
        //Ако няма такава машина, manager-ът ще хвърли exception.
        TuringMachine tm = manager.getMachine(id);

        //Проверка дали машината има инициализирана лента
        if (tm.getTape() == null) {
            throw new MachineNotInitializedException(
                    "Конфигурацията липсва. Машина '" + id + "' не разполага с инициализирана лента."
            );
        }

        //Вземаме съдържанието на лентата и текущата позиция на главата
        String content = tm.getTape().getContent();
        int headPosition = tm.getTape().getHeadPosition();

        System.out.println("\n=== Визуализация на лентата (ID: " + id + ") ===");
        System.out.println(content);

        //Печатаме маркер под текущата позиция на главата
        for (int i = 0; i < headPosition; i++) {
            System.out.print(" ");
        }
        System.out.println("^");

        System.out.println("============================================\n");
    }
}