package com.san4n;

import com.san4n.downloader.Downloader;

public class DownloadManager {

    private final DownloadInfo downloadInfo;

    public DownloadManager(String url){
        downloadInfo = new DownloadInfo(url);
    }

    public void start() {
        Downloader downloader = Downloader.getDownloader(downloadInfo);
        downloader.download(downloadInfo);
    }
}
