package bg.tu_varna.sit.f24621691.project.cli;

public abstract class AbstractCommand implements ICommand {
    private final String usage;
    private final String description;

    public AbstractCommand(String usage, String description) {
        this.usage = usage;
        this.description = description;
    }

    public String getUsage() {
        return usage;
    }

    public String getDescription() {
        return description;
    }

    //Проверява дали броят на аргументите е в допустимите граници
    protected void validateArgs(String[] args, int minArgs, int maxArgs) {
        int count = args == null ? 0 : args.length;

        if (count < minArgs || count > maxArgs) {
            throw new IllegalArgumentException("Употреба: " + usage);
        }
    }
}