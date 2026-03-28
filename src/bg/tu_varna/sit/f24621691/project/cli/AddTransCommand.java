package bg.tu_varna.sit.f24621691.project.cli;

import bg.tu_varna.sit.f24621691.project.core.MachineManager;
import bg.tu_varna.sit.f24621691.project.model.TuringMachine;
import bg.tu_varna.sit.f24621691.project.model.Transition;

public class AddTransCommand implements ICommand {
    private final MachineManager manager;

    public AddTransCommand(MachineManager manager) {
        this.manager = manager;
    }

    @Override
    public void execute(String[] args) {

        if (args.length < 7) {
            System.out.println("Невалидни параметри." +
                    "Пробвай: addTrans <id> <q> <read> <q2> <write> <move>");
            return;
        }

        String id = args[1];
        TuringMachine tm = manager.getMachine(id);

        if (tm == null) {
            System.out.println("Машина с ID '" + id + "' не е намерена.");
            return;
        }

        try {
            //Взимаме данните от масива с аргументи
            String fromState = args[2];
            char readSymbol = args[3].charAt(0);
            String toState = args[4];
            char writeSymbol = args[5].charAt(0);
            char direction = args[6].toUpperCase().charAt(0);

            //Създаваме прехода чрез клас Transition
            Transition newTransition = new Transition(fromState, readSymbol, toState,
                    writeSymbol, direction);

            //Добавяме го в машината
            tm.addTransition(newTransition);

            System.out.println("Успешно добавен преход към машина " + id);
        } catch (Exception e) {
            System.out.println("Невалидни данни за прехода.");
        }
    }
}