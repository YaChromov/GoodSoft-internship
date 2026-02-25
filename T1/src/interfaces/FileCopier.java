package interfaces;

import java.io.*;
import java.nio.file.*;
public interface FileCopier {
    void copy(Path source, Path destination) throws IOException;
}
