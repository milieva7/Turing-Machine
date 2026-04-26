package bg.tu_varna.sit.f24621691.project.cli;

import bg.tu_varna.sit.f24621691.project.core.MachineManager;
import bg.tu_varna.sit.f24621691.project.model.TuringMachine;

public class AddStateCommand implements ICommand {
    private final MachineManager manager;

    public AddStateCommand(MachineManager manager) {
        this.manager = manager;
    }

    @Override
    public void execute(String[] args) {
        // Очакваме: addState <machine_id> <state_name>
        if (args.length < 3) {
            System.out.println("Трябва да напишеш ID на машината и името на състоянието.");
            System.out.println("Пример: addState M1 q_new");
            return;
        }

        String id = args[1];
        String stateName = args[2];

        try {
            // 1. Търсим машината в мениджъра
            TuringMachine tm = manager.getMachine(id);

            if (tm == null) {
                System.out.println("Грешка: Няма машина с такова име '" + id + "'. Провери пак.");
                return;
            }

            // 2. Добавяме състоянието
            tm.addState(stateName);

            // 3. Визуализираме резултата веднага
            System.out.println("Успех! Състояние '" + stateName + "' вече е в отбора.");
            System.out.println("--- Актуален вид на " + id + " ---");
            System.out.println(tm.toString());
            System.out.println("---------------------------");

        } catch (Exception e) {
            System.out.println("Нещо гръмна при добавянето: " + e.getMessage());
        }
    }
}