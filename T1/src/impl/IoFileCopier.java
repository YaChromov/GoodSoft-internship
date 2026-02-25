package impl;

import interfaces.FileCopier;

import java.io.*;
import java.nio.file.Path;

public class IoFileCopier implements FileCopier {
    private static final int BUFFER_SIZE = 8192;
        @Override
        public void copy(Path source, Path destination) throws IOException {

            try (InputStream in = new FileInputStream(source.toFile());
                 OutputStream out = new FileOutputStream(destination.toFile())) {

                byte[] buffer = new byte[BUFFER_SIZE];
                int bytesRead;

                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
            }
        }
    }
