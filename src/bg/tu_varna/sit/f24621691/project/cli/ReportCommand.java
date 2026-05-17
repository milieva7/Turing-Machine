package bg.tu_varna.sit.f24621691.project.cli;

import bg.tu_varna.sit.f24621691.project.core.MachineManager;
import bg.tu_varna.sit.f24621691.project.model.TuringMachine;

/**
 * Команда за извеждане на обобщен отчет за изпълнение на Машина на Тюринг.
 * Командата инициализира машината с входна дума, изпълнява я до спиране
 * или до достигане на максимален брой стъпки и показва резултатите.
 */
public class ReportCommand extends AbstractCommand {
    private final MachineManager manager;

    //Подразбиращ се лимит, ако потребителят не подаде max=<n>
    private static final int DEFAULT_MAX_STEPS = 1000;

    /**
     * Създава команда за обобщен отчет.
     *
     * @param manager мениджърът, от който се взима машината
     */
    public ReportCommand(MachineManager manager) {
        super("report <id> <word> [max=<n>]", "Показва обобщен отчет за изпълнение на машина.");
        this.manager = manager;
    }

    /**
     * Изпълнява командата report.
     * Стартира машината с подадената входна дума и извежда отчет
     * за конфигурацията, изпълнените стъпки, лентата и крайния резултат.
     *
     * @param args аргументи на командата
     * @throws IllegalArgumentException ако параметърът max е в невалиден формат
     */
    @Override
    public void execute(String[] args) {
        //Проверка за правилен брой аргументи.
        //Позволяваме: report <id> <word>; report <id> <word> max=<n>
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

        System.out.println("========== ОТЧЕТ ЗА МАШИНА '" + id + "' ==========");

        //Основна информация за машината
        System.out.println("ID: " + tm.getId());
        System.out.println("Входна дума: " + input);
        System.out.println("Начално състояние: " +
                (tm.getStartState() != null ? tm.getStartState() : "---"));
        System.out.println("Брой състояния: " + tm.getStates().size());
        System.out.println("Брой преходи: " + tm.getTransitions().size());
        System.out.println("Приемащи състояния: " +
                (!tm.getAcceptStates().isEmpty() ? tm.getAcceptStates() : "---"));
        System.out.println("Отхвърлящи състояния: " +
                (!tm.getRejectStates().isEmpty() ? tm.getRejectStates() : "---"));

        //Информация за изпълнението
        System.out.println("Изпълнени стъпки: " + steps);
        System.out.println("Максимален брой стъпки: " + maxSteps);
        System.out.println("Текущо състояние: " + tm.getCurrentState());
        System.out.println("Лента: " + tm.getTape().getContent());
        System.out.println("Позиция на главата: " + tm.getTape().getHeadPosition());

        //Проверка дали машината е приключила или е прекъсната от max лимита
        if (!tm.isHalted()) {
            System.out.println("Изпълнение: ПРЕКЪСНАТО ПОРАДИ ДОСТИГНАТ ЛИМИТ");
            System.out.println("Резултат: машината не приключи в рамките на " + maxSteps + " стъпки.");
        } else {
            System.out.println("Изпълнение: СПРЯЛА");

            if (tm.getAcceptStates().contains(tm.getCurrentState())) {
                System.out.println("Резултат: машината е приела думата.");
            } else if (tm.getRejectStates().contains(tm.getCurrentState())) {
                System.out.println("Резултат: машината е отхвърлила думата.");
            } else if (tm.isHaltedNoTransition()) {
                System.out.println("Резултат: машината е спряла поради липсващ преход.");
            } else {
                System.out.println("Резултат: машината е спряла.");
            }
        }

        System.out.println("==================================================");
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