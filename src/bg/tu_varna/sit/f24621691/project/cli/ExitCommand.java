package bg.tu_varna.sit.f24621691.project.cli;

public class ExitCommand implements ICommand {
    private CommandLineInterface cli;

    public ExitCommand(CommandLineInterface cli) {
        this.cli = cli;
    }

    @Override
    public void execute(String[] args) {

        cli.stop();
    }
}