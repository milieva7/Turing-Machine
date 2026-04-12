package bg.tu_varna.sit.f24621691.project.cli;

import bg.tu_varna.sit.f24621691.project.core.MachineManager;
import bg.tu_varna.sit.f24621691.project.model.TuringMachine;
import bg.tu_varna.sit.f24621691.project.core.exceptions.MachineNotFoundException;
import bg.tu_varna.sit.f24621691.project.model.exceptions.MachineNotInitializedException;

public class StepCommand implements ICommand {
    private final MachineManager manager;

    public StepCommand(MachineManager manager) {
        this.manager = manager;
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 2) {
            System.out.println("Невалиден брой параметри. Правилен формат: 'step <id>'");
            return;
        }

        String id = args[1];

        try {
            TuringMachine tm = manager.getMachine(id);

            //Проверка за съществуване на машината
            if (tm == null) {
                throw new MachineNotFoundException("Машина с идентификатор '" + id + "' не е открита в системата.");
            }

            //Проверка за инициализирана лента
            if (tm.getTape() == null) {
                throw new MachineNotInitializedException("Операцията не може да бъде изпълнена. Машина '" + id + "' не е инициализирана с входна дума.");
            }

            //Проверка на състоянието
            if (tm.isHalted()) {
                System.out.println("Машината е в крайно състояние (" + tm.getCurrentState() + ") и е прекратила работа.");
                return;
            }

            //Изпълнение на стъпката
            tm.step();

            System.out.println("Успешно изпълнена стъпка. Текущо оперативно състояние: " + tm.getCurrentState());

        } catch (MachineNotFoundException e) {
            System.out.println("Грешка при изпълнение: " + e.getMessage());
        } catch (MachineNotInitializedException e) {
            System.out.println("Грешка в жизнения цикъл: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Непредвидено изключение при изпълнение на стъпка. Детайли: " + e.getMessage());
        }
    }
}