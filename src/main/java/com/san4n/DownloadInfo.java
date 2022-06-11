package com.san4n;

import com.san4n.property.ApplicationProperty;
import com.san4n.util.FileExtensionUtil;
import com.san4n.util.FileNameUtil;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.atomic.AtomicLong;

public class DownloadInfo {

    private final String downloadUrl;
    private String fileName;
    private String fileExtension = "";
    private URL nonStringUrl;
    private long fileSize;
    private final AtomicLong downloadedFileSize = new AtomicLong(0L);
    private long filePartitionSize;
    private long remainingByte;
    private RandomAccessFile outputFile;

    private HttpURLConnection httpURLConnection;

    public DownloadInfo(String downloadUrl) {
        this.downloadUrl = downloadUrl;
        intialization();
    }

    private void intialization() {
        try {
            nonStringUrl = new URL(downloadUrl);
            httpURLConnection = (HttpURLConnection) nonStringUrl.openConnection();
            fileSize = httpURLConnection.getContentLengthLong();
            fileExtension = FileExtensionUtil.fileExtension(httpURLConnection);
            fileName = FileNameUtil.fileName(downloadUrl, httpURLConnection,fileExtension);

            outputFile = new RandomAccessFile(fileName+fileExtension,"rw");
            remainingByte = fileSize % ApplicationProperty.THREAD_NUM;
            filePartitionSize = fileSize / ApplicationProperty.THREAD_NUM;
            System.out.println(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "DownloadInfo{\n" +
                "\tdownloadUrl='" + downloadUrl + "',\n" +
                "\tfileName='" + fileName + "',\n" +
                "\tfileExtension='" + fileExtension + "',\n" +
                "\tfileSize=" + fileSize + ",\n"+
                "\tfilePartitionSize=" + filePartitionSize + "\n"+
                '}';
    }

    public long getFilePartitionSize() {
        return filePartitionSize;
    }

    public URL getURL() {
        return nonStringUrl;
    }

    public long getRemainingByte() {
        return remainingByte;
    }

    public void closeFile() {
        try {
            outputFile.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public long getDownloadedFileSize() {
        return downloadedFileSize.get();
    }

    public void updateDownloadedFileSize(long downloadedFileSize) {
        this.downloadedFileSize.addAndGet(downloadedFileSize);
        System.out.print(downloadPercentage()+"%...\r");
    }

    private long downloadPercentage() {
        return ((getDownloadedFileSize()*100 / fileSize));
    }

    public HttpURLConnection getHttpURLConnection() {
        return httpURLConnection;
    }
}
