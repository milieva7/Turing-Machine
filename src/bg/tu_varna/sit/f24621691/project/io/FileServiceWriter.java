package bg.tu_varna.sit.f24621691.project.io;

import bg.tu_varna.sit.f24621691.project.io.exceptions.TuringFileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class FileServiceWriter implements Writer {

    @Override
    public void write(String filePath, List<String> lines) {

        if (filePath == null || filePath.isBlank()) {
            throw new TuringFileNotFoundException("Не е посочен път за запис на файла!");
        }

        //try-with-resources за автоматично затваряне на потока
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            if (lines != null) {
                for (String line : lines) {
                    writer.println(line);
                }
            }
        } catch (IOException e) {
            throw new TuringFileNotFoundException("Неуспешен запис на данни във файл: " + filePath, e);
        }
    }
}