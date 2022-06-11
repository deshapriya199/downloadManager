package com.san4n.downloader.worker;

import com.san4n.DownloadInfo;
import com.san4n.property.ApplicationProperty;

import java.io.*;
import java.net.HttpURLConnection;
import java.util.Objects;
import java.util.concurrent.Callable;

public record SingleWorker(DownloadInfo downloadInfo) implements Callable<Boolean> {


    @Override
    public Boolean call() throws Exception {
        HttpURLConnection httpURLConnection = (HttpURLConnection) downloadInfo.getURL().openConnection();
        httpURLConnection.connect();
        InputStream inputStream = httpURLConnection.getInputStream();
        File file = new File(downloadInfo.getFileName());
        OutputStream outputStream = new FileOutputStream(file);
        transferTo(inputStream, outputStream);
        outputStream.close();
        inputStream.close();
        return Boolean.TRUE;
    }

    private void transferTo(InputStream inputStream, OutputStream outputStream) throws IOException {
        Objects.requireNonNull(outputStream, "out");
        byte[] buffer = new byte[ApplicationProperty.DEFAULT_BUFFER_SIZE];
        int read;
        while ((read = inputStream.read(buffer, 0, ApplicationProperty.DEFAULT_BUFFER_SIZE)) >= 0) {
            outputStream.write(buffer, 0, read);
            downloadInfo.updateDownloadedFileSize(read);
        }
    }
}
