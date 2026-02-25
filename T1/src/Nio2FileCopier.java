import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Nio2FileCopier implements FileCopier  {
    @Override
    public void copy(Path source, Path destination) throws IOException {
       Files.copy(source, destination);
    }
}
