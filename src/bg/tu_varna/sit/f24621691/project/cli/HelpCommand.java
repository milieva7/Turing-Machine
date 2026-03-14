package bg.tu_varna.sit.f24621691.project.cli;

public class HelpCommand implements ICommand {
    @Override
    public void execute(String[] args) {
        System.out.println("""
            Налични команди:
            list - Списък на заредените машини
            help - Показва това меню
            exit - Изход от програмата
            """);
    }
}