package com.san4n.downloader;

import com.san4n.DownloadInfo;
import com.san4n.downloader.worker.MultiPartWorker;
import com.san4n.property.ApplicationProperty;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MultiThreadDownloader implements Downloader{
    @Override
    public void download(DownloadInfo downloadInfo) {
        ExecutorService thread_pool = Executors.newFixedThreadPool(ApplicationProperty.THREAD_NUM);

        for(int i = 1; i <= ApplicationProperty.THREAD_NUM; i++){
            thread_pool.submit(new MultiPartWorker(i, downloadInfo));
        }

        thread_pool.shutdown();

        try {
            thread_pool.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
        } catch (InterruptedException ex) {
            Logger.getLogger(DownloadInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
        downloadInfo.closeFile();
    }
}
