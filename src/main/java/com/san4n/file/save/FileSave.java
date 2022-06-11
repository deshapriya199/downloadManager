package com.san4n.file.save;

import com.san4n.DownloadInfo;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.function.Function;

public record FileSave(DownloadInfo downloadInfo, Path path, InputStream inputStream) {

    private static final int DEFAULT_BUFFER_SIZE = 8192;

    public static final Function<String, Path> PATH_FUNCTION = fileName -> Paths.get("temp", fileName);

    public static FileSaveBuilder builder() {
        return new FileSaveBuilder();
    }

    public void download() {
        try {
            OutputStream outputStream = new FileOutputStream(path.toFile());
            transferTo(inputStream, outputStream);
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void transferTo(InputStream inputStream, OutputStream outputStream) throws IOException {
        Objects.requireNonNull(outputStream, "out");
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        int read;
        while ((read = inputStream.read(buffer, 0, DEFAULT_BUFFER_SIZE)) >= 0) {
            outputStream.write(buffer, 0, read);
            downloadInfo.updateDownloadedFileSize(read);
        }
    }

    public void mergeToMainFile(long position) throws IOException {
        try (
                RandomAccessFile outputFile = new RandomAccessFile(downloadInfo.getFileName() + downloadInfo.getFileExtension(), "rw");
                FileInputStream file = new FileInputStream(path.toFile())
        ) {
            outputFile.seek(position);
            file.getChannel().transferTo(0, Long.MAX_VALUE, outputFile.getChannel());
        }
    }

    public boolean deleteTempFile() throws IOException {
        return Files.deleteIfExists(path);
    }

    public static class FileSaveBuilder {
        private DownloadInfo downloadInfo;
        private Path path;
        private InputStream inputStream;

        private FileSaveBuilder() {
        }

        public FileSaveBuilder downloadInfo(DownloadInfo downloadInfo) {
            this.downloadInfo = downloadInfo;
            return this;
        }

        public FileSaveBuilder inputStream(InputStream inputStream) {
            this.inputStream = inputStream;
            return this;
        }

        public FileSaveBuilder fileName(String fileName) {
            path = PATH_FUNCTION.apply(fileName);
            return this;
        }

        public FileSave build() {
            return new FileSave(downloadInfo, path, inputStream);
        }
    }
}
