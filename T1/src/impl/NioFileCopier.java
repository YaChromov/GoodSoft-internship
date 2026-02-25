package impl;

import interfaces.FileCopier;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Path;

public class NioFileCopier implements FileCopier {
    @Override
    public void copy(Path source, Path destination) throws IOException {

        try (FileChannel sourceChannel = new FileInputStream(source.toFile()).getChannel();
             FileChannel destChannel = new FileOutputStream(destination.toFile()).getChannel()) {

            destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
        }
    }
}