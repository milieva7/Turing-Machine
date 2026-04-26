package bg.tu_varna.sit.f24621691.project.cli;

import bg.tu_varna.sit.f24621691.project.core.MachineManager;
import bg.tu_varna.sit.f24621691.project.model.TuringMachine;
import bg.tu_varna.sit.f24621691.project.core.exceptions.MachineNotFoundException;
import bg.tu_varna.sit.f24621691.project.model.exceptions.MachineNotInitializedException;

public class TapeCommand implements ICommand {
    private final MachineManager manager;

    public TapeCommand(MachineManager manager) {
        this.manager = manager;
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 2) {
            System.out.println("Невалиден брой параметри. Правилен формат: 'tape <id>'");
            return;
        }

        String id = args[1];

        try {
            TuringMachine tm = manager.getMachine(id);

            //Проверка дали машината съществува
            if (tm == null) {
                throw new MachineNotFoundException("Машина с идентификатор '" + id + "' не е заредена в системата.");
            }

            //Проверка дали има активна лента
            if (tm.getTape() == null) {
                throw new MachineNotInitializedException("Конфигурацията липсва. Машина '" + id + "' не разполага с инициализирана лента.");
            }


            System.out.println("\n=== Визуализация на лентата (ID: " + id + ") ===");
            System.out.println(tm.getTape().toString());
            System.out.println("============================================\n");

        } catch (MachineNotFoundException e) {
            System.out.println("Грешка при достъп: " + e.getMessage());
        } catch (MachineNotInitializedException e) {
            System.out.println("Грешка в състоянието: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Системна грешка: Неочаквано прекъсване при четене на лентата. Детайли: " + e.getMessage());
        }
    }
}