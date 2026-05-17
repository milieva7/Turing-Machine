package bg.tu_varna.sit.f24621691.project.cli;

import bg.tu_varna.sit.f24621691.project.core.MachineManager;
import bg.tu_varna.sit.f24621691.project.io.exceptions.TuringFileNotFoundException;

/**
 * Команда за запис на всички заредени машини в текущо отворения файл.
 * Използва сериализираните данни от MachineManager и ги записва чрез FileServiceWriter.
 */
public class SaveCommand extends AbstractCommand {
    private final CommandLineInterface cli;
    private final MachineManager manager;

    /**
     * Създава команда за запис в текущия файл.
     *
     * @param cli командният интерфейс, от който се взима текущият файл и FileServiceWriter
     * @param manager мениджърът, който съхранява заредените машини
     */
    public SaveCommand(CommandLineInterface cli, MachineManager manager) {
        super("save", "Записва всички заредени машини в текущо отворения файл.");
        this.cli = cli;
        this.manager = manager;
    }

    /**
     * Изпълнява командата save.
     * Записва всички заредени машини в текущо отворения файл.
     *
     * @param args аргументи на командата
     * @throws TuringFileNotFoundException ако няма отворен файл за запис
     */
    @Override
    public void execute(String[] args) {
        //Проверка за правилен брой аргументи.
        //Командата save не приема допълнителни параметри.
        validateArgs(args, 1, 1);

        String path = cli.getCurrentFilePath();

        //Проверка дали има отворен файл,
        //в който да се запише текущото състояние
        if (path == null || path.isBlank()) {
            throw new TuringFileNotFoundException(
                    "Няма отворен файл за запис! Използвай 'saveas' или първо 'open'."
            );
        }

        //Записваме всички машини от manager-а във файла
        cli.getFileWriter().write(path, manager.getSerializableData());

        System.out.println("Успешно запазване в: " + extractFileName(path));
    }

    /**
     * Извлича името на файла от пълния път.
     *
     * @param path пълният път до файла
     * @return само името на файла или "unknown", ако пътят е null
     */
    private String extractFileName(String path) {
        if (path == null) {
            return "unknown";
        }

        int lastIdx = Math.max(path.lastIndexOf('\\'), path.lastIndexOf('/'));
        return (lastIdx != -1) ? path.substring(lastIdx + 1) : path;
    }
}