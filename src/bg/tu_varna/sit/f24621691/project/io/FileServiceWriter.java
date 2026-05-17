package bg.tu_varna.sit.f24621691.project.io;

import bg.tu_varna.sit.f24621691.project.io.exceptions.TuringFileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Клас за запис на данни във файл.
 * Реализира интерфейса Writer и предоставя метод за запис на списък от редове.
 */
public class FileServiceWriter implements Writer {

    /**
     * Записва списък от редове във файл.
     * Файлът се презаписва с новото съдържание.
     *
     * @param filePath пътят до файла, в който ще се записва
     * @param lines списък с редове за запис
     * @throws TuringFileNotFoundException ако пътят е невалиден или възникне грешка при запис
     */
    @Override
    public void write(String filePath, List<String> lines) {
        validatePath(filePath);

        //за да презапишем файла с актуалните данни
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, false))) {
            if (lines != null) {
                for (String line : lines) {
                    writer.println(line);
                }
            }
        } catch (IOException e) {
            throw new TuringFileNotFoundException("Грешка при масов запис във файл: " + filePath, e);
        }
    }

    /**
     * Проверява дали пътят до файла е валиден.
     *
     * @param filePath пътят до файла
     * @throws TuringFileNotFoundException ако пътят е null или празен
     */
    private void validatePath(String filePath) {
        if (filePath == null || filePath.isBlank()) {
            throw new TuringFileNotFoundException("Операцията е прекъсната: Невалиден път до файл.");
        }
    }
}