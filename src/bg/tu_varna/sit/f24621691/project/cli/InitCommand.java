package bg.tu_varna.sit.f24621691.project.cli;

import bg.tu_varna.sit.f24621691.project.core.MachineManager;
import bg.tu_varna.sit.f24621691.project.model.TuringMachine;
import bg.tu_varna.sit.f24621691.project.core.exceptions.MachineNotFoundException;
import bg.tu_varna.sit.f24621691.project.cli.exceptions.ConfigurationException;

public class InitCommand implements ICommand {
    private final MachineManager manager;

    public InitCommand(MachineManager manager) {
        this.manager = manager;
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 3) {
            System.out.println("Трябва да подадеш ID и дума! Пробвай: init <id> <input>");
            return;
        }

        String id = args[1];
        String input = args[2];

        try {
            TuringMachine tm = manager.getMachine(id);

            //Проверка дали има такава машината
            if (tm == null) {
                throw new MachineNotFoundException("Не е намерена машина с ID '" + id + "'.");
            }

            //Проверка за празна дума
            if (input.trim().isEmpty()) {
                throw new ConfigurationException("Не можеш да пуснеш празна дума!");
            }

            tm.init(input);

            System.out.println("Машина " + id + " е инициализирана с дума: " + input);
            System.out.println("Текущо състояние: " + tm.getCurrentState());

        } catch (MachineNotFoundException e) {
            System.out.println("Неуспешна инициализация. " + e.getMessage());
        } catch (ConfigurationException e) {
            System.out.println("Невалидна конфигурация на входните данни. " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Възникна неочаквано изключение при изпълнение на командата. Детайли: " + e.getMessage());
        }
    }
}