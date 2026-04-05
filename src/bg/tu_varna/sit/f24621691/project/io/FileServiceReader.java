package bg.tu_varna.sit.f24621691.project.core;

import bg.tu_varna.sit.f24621691.project.io.exceptions.TuringFileNotFoundException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class FileServiceReader {

    /*
      Чете съдържанието на файл.
      Ако файлът не съществува, създава нов празен файл.
     */
    public List<String> read(String path) {
        if (path == null || path.isBlank()) {
            throw new IllegalArgumentException("Пътят до файла не може да бъде празен!");
        }

        File file = new File(path);

        try {
            //Ако файлът не съществува
            if (!file.exists()) {
                //Създаваме нов празен файл на диска
                boolean created = file.createNewFile();
                if (created) {
                    System.out.println("Файлът не съществуваше, беше създаден нов празен файл: " + path);
                }
                //Връщаме празен списък, защото файлът току-що е създаден и няма нищо за парсване
                return new ArrayList<>();
            }

            //Ако файлът съществува, четем всички редове
            return Files.readAllLines(file.toPath());

        } catch (IOException e) {
            //Ако няма права за запис, диска е пълен и т.н.
            throw new TuringFileNotFoundException("Грешка при работа с файл: " + path, e);
        }
    }
}