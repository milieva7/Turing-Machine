package bg.tu_varna.sit.f24621691.project.io;

import java.util.List;

/**
 * Интерфейс за четене на съдържание от файл.
 * Определя общ метод, който трябва да бъде реализиран от класовете за четене.
 */
public interface Reader {

    /**
     * Чете съдържанието на файл и го връща като списък от редове.
     *
     * @param path пътят до файла, който ще бъде прочетен
     * @return списък с редовете от файла
     */
    List<String> read(String path);
}