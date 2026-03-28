package bg.tu_varna.sit.f24621691.project.io;

import java.io.IOException;
import java.util.List;

public interface Writer {
    void write(String path, List<String> lines) throws IOException;
}