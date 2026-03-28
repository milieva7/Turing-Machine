package bg.tu_varna.sit.f24621691.project.io;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class FileServiceWriter implements Writer {

    @Override
    public void write(String filePath, List<String> lines) throws IOException {

        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (String line : lines) {
                writer.println(line);
            }
        }
    }
}