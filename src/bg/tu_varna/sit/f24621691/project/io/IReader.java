package bg.tu_varna.sit.f24621691.project.io;

import java.io.IOException;
import java.util.List;

public interface IReader {
    List<String> read(String path) throws IOException;
}
