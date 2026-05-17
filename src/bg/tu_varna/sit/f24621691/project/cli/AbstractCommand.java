package bg.tu_varna.sit.f24621691.project.cli;

/**
 * Абстрактен базов клас за всички команди в командния интерфейс.
 * Съдържа обща информация за употреба, описание и проверка на аргументите.
 */
public abstract class AbstractCommand implements ICommand {
    private final String usage;
    private final String description;

    /**
     * Създава базова команда с начин на употреба и описание.
     *
     * @param usage начинът на използване на командата
     * @param description кратко описание на командата
     */
    public AbstractCommand(String usage, String description) {
        this.usage = usage;
        this.description = description;
    }

    /**
     * Връща начина на използване на командата.
     *
     * @return текст с употребата на командата
     */
    public String getUsage() {
        return usage;
    }

    /**
     * Връща кратко описание на командата.
     *
     * @return описание на командата
     */
    public String getDescription() {
        return description;
    }

    /**
     * Проверява дали броят на подадените аргументи е в допустимите граници.
     *
     * @param args аргументите, подадени към командата
     * @param minArgs минимален допустим брой аргументи
     * @param maxArgs максимален допустим брой аргументи
     * @throws IllegalArgumentException ако броят на аргументите е невалиден
     */
    protected void validateArgs(String[] args, int minArgs, int maxArgs) {
        int count = args == null ? 0 : args.length;

        if (count < minArgs || count > maxArgs) {
            throw new IllegalArgumentException("Употреба: " + usage);
        }
    }
}