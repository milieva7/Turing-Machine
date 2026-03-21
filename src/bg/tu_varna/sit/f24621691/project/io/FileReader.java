package bg.tu_varna.sit.f24621691.project.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileReader implements IReader {
    @Override
    public List<String> read(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            Files.createFile(path);
            System.out.println("Файлът не съществуваше и беше създаден нов.");
        }
        return Files.readAllLines(path);
    }
}