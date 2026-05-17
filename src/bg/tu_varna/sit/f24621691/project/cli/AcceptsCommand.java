package bg.tu_varna.sit.f24621691.project.cli;

import bg.tu_varna.sit.f24621691.project.core.MachineManager;
import bg.tu_varna.sit.f24621691.project.model.TuringMachine;

/**
 * Команда за проверка дали Машина на Тюринг приема дадена входна дума.
 * Командата инициализира машината с подадената дума и я изпълнява
 * до спиране или до достигане на максимален брой стъпки.
 */
public class AcceptsCommand extends AbstractCommand {
    private final MachineManager manager;

    //Подразбиращ се лимит, ако потребителят не подаде max=<n>
    private static final int DEFAULT_MAX_STEPS = 1000;

    /**
     * Създава команда за проверка на входна дума.
     *
     * @param manager мениджърът, от който се взима машината
     */
    public AcceptsCommand(MachineManager manager) {
        super("accepts <id> <word> [max=<n>]", "Проверява дали машината приема дадена дума.");
        this.manager = manager;
    }

    /**
     * Изпълнява командата accepts.
     * Стартира машината с подадената дума и проверява дали тя достига приемащо състояние.
     *
     * @param args аргументи на командата
     * @throws IllegalArgumentException ако параметърът max е в невалиден формат
     */
    @Override
    public void execute(String[] args) {
        //Проверка за правилен брой аргументи.
        //Позволяваме: accepts <id> <word>; accepts <id> <word> max=<n>
        validateArgs(args, 3, 4);

        String id = args[1];
        String input = args[2];

        //Взимаме максималния брой стъпки.
        //Ако няма подаден max=<n>, използваме DEFAULT_MAX_STEPS.
        int maxSteps = getMaxSteps(args);

        //Търсим машината по ID.
        //Ако няма такава машина, manager-ът ще хвърли exception.
        TuringMachine tm = manager.getMachine(id);

        //Инициализираме машината с подадената дума
        tm.init(input);

        int steps = 0;

        //Изпълняваме машината, докато не спре или докато не стигнем лимита
        while (!tm.isHalted() && steps < maxSteps) {
            tm.step();
            steps++;
        }

        //Ако машината още не е спряла, значи сме стигнали max лимита
        if (!tm.isHalted()) {
            System.out.println("Достигнат е максималният брой стъпки: " + maxSteps);
            System.out.println("Машина '" + id + "' не приключи изпълнението за думата \"" + input + "\".");
            return;
        }

        //Проверяваме в какво крайно състояние е спряла машината
        if (tm.getAcceptStates().contains(tm.getCurrentState())) {
            System.out.println("Машина '" + id + "' ПРИЕМА думата \"" + input + "\".");
        } else if (tm.getRejectStates().contains(tm.getCurrentState())) {
            System.out.println("Машина '" + id + "' НЕ приема думата \"" + input + "\".");
        } else if (tm.isHaltedNoTransition()) {
            System.out.println("Машина '" + id + "' спря поради липсващ преход и НЕ приема думата \"" + input + "\".");
        } else {
            System.out.println("Машина '" + id + "' спря без приемащо състояние за думата \"" + input + "\".");
        }
    }

    /**
     * Взима максималния брой стъпки от аргументите на командата.
     * Ако не е подаден max параметър, връща подразбиращия се лимит.
     *
     * @param args аргументите на командата
     * @return максималният брой стъпки
     * @throws IllegalArgumentException ако max параметърът е в невалиден формат
     */
    private int getMaxSteps(String[] args) {
        //Ако няма четвърти аргумент, връщаме подразбиращия се лимит
        if (args.length < 4) {
            return DEFAULT_MAX_STEPS;
        }

        String maxArg = args[3];

        //Проверка дали аргументът е във формат max=<n>
        if (!maxArg.startsWith("max=")) {
            throw new IllegalArgumentException("Невалиден параметър. Използвай: max=<n>");
        }

        String value = maxArg.replace("max=", "");

        try {
            int maxSteps = Integer.parseInt(value);

            if (maxSteps <= 0) {
                throw new IllegalArgumentException("max трябва да бъде положително число.");
            }

            return maxSteps;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("max трябва да бъде число.");
        }
    }
}