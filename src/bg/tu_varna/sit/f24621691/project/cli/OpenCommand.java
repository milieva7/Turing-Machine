package bg.tu_varna.sit.f24621691.project.cli;

import java.io.IOException;
import java.util.List;

public class OpenCommand implements ICommand {
    private final CommandLineInterface cli;

    public OpenCommand(CommandLineInterface cli) {
        this.cli = cli;
    }

    @Override
    public void execute(String[] args) {
        //Проверка дали потребителят е подал път до файл
        if (args.length < 2) {
            System.out.println("Грешка: Моля, посочете път до файл. Пример: open C:\\temp\\file.xml");
            return;
        }

        String path = args[1];

        try {
            //Опитваме се да прочетем файла чрез вградения в CLI reader
            List<String> content = cli.getFileReader().read(path);

            //Ако стигнем до тук, значи файлът е зареден или създаден успешно
            cli.setCurrentFilePath(path);

            System.out.println("Successfully opened " + extractFileName(path));

            //Тук по-късно ще добавим логика за парсване на съдържанието
            if (content.isEmpty()) {
                System.out.println("Файлът е празен. Готов за нови данни.");
            } else {
                System.out.println("Прочетени са " + content.size() + " реда.");
            }

        } catch (IOException e) {
            System.out.println("Грешка при работа с файла: " + e.getMessage());
        }
    }

    //Помощен метод за извличане само на името на файла от пълния път
    private String extractFileName(String path) {
        return path.substring(path.lastIndexOf('\\') + 1);
    }
}