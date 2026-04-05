package bg.tu_varna.sit.f24621691.project.cli;

import bg.tu_varna.sit.f24621691.project.core.MachineManager;
import bg.tu_varna.sit.f24621691.project.model.TuringMachine;
import bg.tu_varna.sit.f24621691.project.model.Transition;
import bg.tu_varna.sit.f24621691.project.cli.exceptions.ConfigurationException;

public class AddTransCommand implements ICommand {
    private final MachineManager manager;

    public AddTransCommand(MachineManager manager) {
        this.manager = manager;
    }

    @Override
    public void execute(String[] args) {
        //Проверка за брой параметри
        if (args.length < 7) {
            throw new IllegalArgumentException("Грешка: Недостатъчно параметри! " +
                    "Формат: addtrans <id> <q> <read> <q2> <write> <move>");
        }

        String id = args[1];

        // Взимаме машината, ако няма, мениджърът пуска MachineNotFoundException
        TuringMachine tm = manager.getMachine(id);

        try {
            //Взимаме данните от масива
            String fromState = args[2];
            char readSymbol = args[3].charAt(0);
            String toState = args[4];
            char writeSymbol = args[5].charAt(0);
            char direction = args[6].toUpperCase().charAt(0);

            //Създаваме прехода
            // Ако посоката е грешна, конструкторът на Transition ще хвърли InvalidDirectionException
            Transition newTransition = new Transition(fromState, readSymbol, toState, writeSymbol, direction);

            //Добавяме го в машината
            tm.addTransition(newTransition);

            System.out.println("Успешно добавен преход към машина: " + id);

        }  catch (IndexOutOfBoundsException e) {
        throw new ConfigurationException("Данните за прехода са непълни или грешни.");
    } catch (Exception e) {
        throw new ConfigurationException("Грешка при добавяне: " + e.getMessage());

    }
}
}