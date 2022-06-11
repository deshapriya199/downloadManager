package com.san4n.downloader.worker;

import com.san4n.DownloadInfo;
import com.san4n.file.save.FileSave;
import com.san4n.property.ApplicationProperty;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.concurrent.Callable;

public class MultiPartWorker implements Callable<Boolean> {

    private long startByte;
    private long endByte;
    private final int threadNum;
    private final DownloadInfo downloadInfo;
    private long position;

    public MultiPartWorker(int threadNum, DownloadInfo downloadInfo) {
        this.threadNum = threadNum;
        this.downloadInfo = downloadInfo;
        setStartAndEndByte();
    }

    private void setStartAndEndByte() {
        if(ApplicationProperty.THREAD_NUM.equals(threadNum)){
            startByte = downloadInfo.getFilePartitionSize() * (threadNum -1);
            endByte = (downloadInfo.getFilePartitionSize() * threadNum) -1 + downloadInfo.getRemainingByte();
        }else{
            startByte = downloadInfo.getFilePartitionSize() * (threadNum -1);
            endByte = (downloadInfo.getFilePartitionSize() * threadNum) -1;
        }
        position = startByte;
    }

    @Override
    public Boolean call() throws Exception {

        HttpURLConnection httpURLConnection = (HttpURLConnection) downloadInfo.getURL().openConnection();
        httpURLConnection.setRequestProperty("Range", "bytes="+startByte+"-"+endByte);
        httpURLConnection.connect();
        InputStream stream = httpURLConnection.getInputStream();

        FileSave fileSave = FileSave.builder()
                .downloadInfo(downloadInfo)
                .inputStream(stream)
                .fileName(downloadInfo.getFileName()+ threadNum)
                .build();

        fileSave.download();
        fileSave.mergeToMainFile(position);
        fileSave.deleteTempFile();

        return Boolean.TRUE;
    }
}
