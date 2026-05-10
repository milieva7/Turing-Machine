package bg.tu_varna.sit.f24621691.project.cli;

import bg.tu_varna.sit.f24621691.project.core.MachineManager;
import bg.tu_varna.sit.f24621691.project.model.TuringMachine;

public class AcceptsCommand extends AbstractCommand {
    private final MachineManager manager;

    public AcceptsCommand(MachineManager manager) {
        super("accepts <id> <word>", "Проверява дали машината приема дадена дума.");
        this.manager = manager;
    }

    @Override
    public void execute(String[] args) {
        //Проверка за правилен брой аргументи
        validateArgs(args, 3, 3);

        String id = args[1];
        String input = args[2];

        //Търсим машината по ID.
        //Ако няма такава машина, manager-ът ще хвърли exception.
        TuringMachine tm = manager.getMachine(id);

        //Инициализираме машината с подадената дума
        tm.init(input);

        //Изпълняваме машината, докато не спре
        while (!tm.isHalted()) {
            tm.step();
        }

        //Проверяваме в какво крайно състояние е спряла машината
        if (tm.getAcceptStates().contains(tm.getCurrentState())) {
            System.out.println("Машина '" + id + "' ПРИЕМА думата \"" + input + "\".");
        } else if (tm.getRejectStates().contains(tm.getCurrentState())) {
            System.out.println("Машина '" + id + "' НЕ приема думата \"" + input + "\".");
        } else {
            System.out.println("Машина '" + id + "' спря без приемащо състояние за думата \"" + input + "\".");
        }
    }
}