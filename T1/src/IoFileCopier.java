import java.io.*;
import java.nio.file.Path;

public class IoFileCopier implements FileCopier {
        @Override
        public void copy(Path source, Path destination) throws IOException {

            try (InputStream in = new FileInputStream(source.toFile());
                 OutputStream out = new FileOutputStream(destination.toFile())) {

                byte[] buffer = new byte[8192];
                int bytesRead;

                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
            }
        }
    }
