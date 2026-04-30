package bg.tu_varna.sit.f24621691.project.cli;

import bg.tu_varna.sit.f24621691.project.core.MachineManager;

import java.util.Set;

public class ListCommand extends AbstractCommand {
    private final MachineManager manager;

    public ListCommand(MachineManager manager) {
        super("list", "Показва ID-тата на всички заредени машини.");
        this.manager = manager;
    }

    @Override
    public void execute(String[] args) {
        //Командата list не приема допълнителни аргументи
        validateArgs(args, 1, 1);

        //Ако няма заредени машини, показваме подходящо съобщение
        if (!manager.hasMachines()) {
            System.out.println("В момента няма заредени машини в симулатора.");
            return;
        }

        //Вземаме ID-тата на всички машини
        Set<String> ids = manager.getAllMachineIds();

        System.out.println("=== Списък на заредените Тюринг машини ===");

        int count = 1;
        for (String id : ids) {
            System.out.println(count + ". ID: " + id);
            count++;
        }

        System.out.println("Общо: " + ids.size() + " машини.");
        System.out.println("==========================================");
    }
}