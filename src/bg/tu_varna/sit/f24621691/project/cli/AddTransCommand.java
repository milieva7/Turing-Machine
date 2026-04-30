package bg.tu_varna.sit.f24621691.project.cli;

import bg.tu_varna.sit.f24621691.project.cli.exceptions.ConfigurationException;
import bg.tu_varna.sit.f24621691.project.core.MachineManager;
import bg.tu_varna.sit.f24621691.project.model.TuringMachine;
import bg.tu_varna.sit.f24621691.project.model.Transition;

public class AddTransCommand extends AbstractCommand {
    private final MachineManager manager;

    public AddTransCommand(MachineManager manager) {
        super("addtrans <id> <q> <read> <q2> <write> <move>",
                "Добавя нов преход към дадена машина.");
        this.manager = manager;
    }

    @Override
    public void execute(String[] args) {
        // Проверка за правилен брой аргументи
        validateArgs(args, 7, 7);

        String id = args[1];

        //Взимаме машината по ID.
        //Ако такава машина няма, manager-ът ще хвърли MachineNotFoundException.
        TuringMachine tm = manager.getMachine(id);

        try {
            //Вземаме данните за прехода от аргументите
            String fromState = args[2];
            char readSymbol = args[3].charAt(0);
            String toState = args[4];
            char writeSymbol = args[5].charAt(0);
            char direction = args[6].toUpperCase().charAt(0);

            //Създаваме новия преход.
            //Ако посоката е невалидна, конструкторът на Transition ще хвърли exception.
            Transition newTransition = new Transition(
                    fromState,
                    readSymbol,
                    toState,
                    writeSymbol,
                    direction
            );

            //Добавяме прехода към машината.
            //Ако състоянията липсват или има недетерминираност,
            //TuringMachine ще хвърли подходящ exception.
            tm.addTransition(newTransition);

            System.out.println("Успешно добавен преход към машина '" + id + "'.");
        } catch (IndexOutOfBoundsException e) {
            //Защита при счупен/непълен вход
            throw new ConfigurationException("Данните за прехода са непълни или грешни.");
        } catch (Exception e) {

            throw new ConfigurationException("Грешка при добавяне на преход: " + e.getMessage());
        }
    }
}