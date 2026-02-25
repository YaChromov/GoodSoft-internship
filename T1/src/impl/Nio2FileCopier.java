package impl;

import interfaces.FileCopier;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class Nio2FileCopier implements FileCopier {
    @Override
    public void copy(Path source, Path destination) throws IOException {
       Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
    }
}
