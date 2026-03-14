package bg.tu_varna.sit.f24621691.project.cli;

public class ExitCommand implements ICommand {
    @Override
    public void execute(String[] args) {
        System.out.println("Програмата се затваря...");
        System.exit(0);
    }
}