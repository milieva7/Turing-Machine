package bg.tu_varna.sit.f24621691.project.io;

import bg.tu_varna.sit.f24621691.project.io.exceptions.TuringFileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class FileServiceWriter implements Writer {

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

    @Override
    public void writeSingle(String filePath, String data) {
        validatePath(filePath);

        //за да може saveTM да актуализира машината, а не да я лепи отдолу
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, false))) {
            if (data != null) {
                //Записваме данните
                writer.println(data);

                //Слагаме маркера за край
                writer.println("---");
            }
        } catch (IOException e) {
            throw new TuringFileNotFoundException("Грешка при запис на машина във файл: " + filePath, e);
        }
    }

    private void validatePath(String filePath) {
        if (filePath == null || filePath.isBlank()) {
            throw new TuringFileNotFoundException("Операцията е прекъсната: Невалиден път до файл.");
        }
    }
}