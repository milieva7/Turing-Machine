package bg.tu_varna.sit.f24621691.project.cli;

import bg.tu_varna.sit.f24621691.project.core.MachineManager;
import bg.tu_varna.sit.f24621691.project.model.TuringMachine;
import bg.tu_varna.sit.f24621691.project.model.Transition;

import java.util.HashSet;
import java.util.Set;

/**
 * Команда за проверка дали конкретна Машина на Тюринг е детерминирана.
 * Машината е детерминирана, ако няма два прехода със същото начално състояние
 * и същия символ за четене.
 */
public class CheckDetCommand extends AbstractCommand {
    private final MachineManager manager;

    /**
     * Създава команда за проверка на детерминираност.
     *
     * @param manager мениджърът, от който се взима машината
     */
    public CheckDetCommand(MachineManager manager) {
        super("checkdet <id>", "Проверява дали машината е детерминирана.");
        this.manager = manager;
    }

    /**
     * Изпълнява командата checkdet.
     * Обхожда всички преходи на машината и проверява дали има повтаряща се двойка
     * от начално състояние и символ за четене.
     *
     * @param args аргументи на командата
     */
    @Override
    public void execute(String[] args) {
        //Проверка за правилен брой аргументи
        validateArgs(args, 2, 2);

        String id = args[1];

        //Търсим машината по ID.
        //Ако няма такава машина, manager-ът ще хвърли exception.
        TuringMachine tm = manager.getMachine(id);

        //Множество, в което пазим вече срещнатите двойки (състояние, символ)
        Set<String> seenPairs = new HashSet<>();

        //Обхождаме всички преходи на машината
        for (Transition t : tm.getTransitions()) {
            String key = t.getFromState() + "|" + t.getReadSymbol();

            //Ако двойката вече съществува, машината не е детерминирана
            if (seenPairs.contains(key)) {
                System.out.println("Машина '" + id + "' НЕ е детерминирана.");
                System.out.println("Намерени са поне два прехода за (" +
                        t.getFromState() + ", " + t.getReadSymbol() + ").");
                return;
            }

            seenPairs.add(key);
        }

        //Ако не сме намерили дублираща се двойка, машината е детерминирана
        System.out.println("Машина '" + id + "' е детерминирана.");
    }
}