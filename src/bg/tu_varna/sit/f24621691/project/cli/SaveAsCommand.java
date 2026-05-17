package bg.tu_varna.sit.f24621691.project.cli;

import bg.tu_varna.sit.f24621691.project.core.MachineManager;

/**
 * Команда за запис на заредените машини в нов файл.
 * След успешен запис новият файл става текущо отворен файл в приложението.
 */
public class SaveAsCommand extends AbstractCommand {
    private final CommandLineInterface cli;
    private final MachineManager manager;

    /**
     * Създава команда за запис в нов файл.
     *
     * @param cli командният интерфейс, чрез който се достъпва FileServiceWriter и текущият файл
     * @param manager мениджърът, който съхранява заредените машини
     */
    public SaveAsCommand(CommandLineInterface cli, MachineManager manager) {
        super("saveas <path>", "Записва всички заредени машини в нов файл и го прави текущ.");
        this.cli = cli;
        this.manager = manager;
    }

    /**
     * Изпълнява командата saveas.
     * Записва всички заредени машини в подадения файл и го задава като текущ.
     *
     * @param args аргументи на командата
     */
    @Override
    public void execute(String[] args) {
        //Проверка за правилен брой аргументи
        validateArgs(args, 2, 2);

        String newPath = args[1];

        //Записваме всички машини в новия файл
        cli.getFileWriter().write(newPath, manager.getSerializableData());

        //Новият файл става текущ отворен файл
        cli.setCurrentFilePath(newPath);

        System.out.println("Успешно записано като: " + extractFileName(newPath));
    }

    /**
     * Извлича името на файла от пълния път.
     *
     * @param path пълният път до файла
     * @return само името на файла
     */
    private String extractFileName(String path) {
        int lastIdx = Math.max(path.lastIndexOf('\\'), path.lastIndexOf('/'));
        return (lastIdx == -1) ? path : path.substring(lastIdx + 1);
    }
}