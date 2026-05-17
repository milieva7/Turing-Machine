package bg.tu_varna.sit.f24621691.project.cli;

/**
 * Команда за изход от програмата.
 * Използва CommandLineInterface, за да спре главния цикъл на изпълнение.
 */
public class ExitCommand implements ICommand {
    private CommandLineInterface cli;

    /**
     * Създава команда за изход.
     *
     * @param cli командният интерфейс, който трябва да бъде спрян
     */
    public ExitCommand(CommandLineInterface cli) {
        this.cli = cli;
    }

    /**
     * Изпълнява командата exit.
     * Спира главния цикъл на командния интерфейс.
     *
     * @param args аргументи на командата
     */
    @Override
    public void execute(String[] args) {
        cli.stop();
    }
}