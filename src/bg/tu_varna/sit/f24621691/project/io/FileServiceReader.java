package bg.tu_varna.sit.f24621691.project.io;

import bg.tu_varna.sit.f24621691.project.io.exceptions.TuringFileNotFoundException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * Клас за четене на съдържание от файл.
 * Реализира интерфейса Reader и връща съдържанието на файла като списък от редове.
 * Ако файлът не съществува, създава нов празен файл.
 */
public class FileServiceReader implements Reader {

    /**
     * Чете съдържанието на файл.
     * Ако файлът не съществува, създава нов празен файл и връща празен списък.
     *
     * @param path пътят до файла, който ще бъде прочетен
     * @return списък с редовете от файла или празен списък, ако файлът е новосъздаден
     * @throws IllegalArgumentException ако пътят до файла е null или празен
     * @throws TuringFileNotFoundException ако възникне грешка при работа с файла
     */
    @Override
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