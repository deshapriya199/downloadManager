package com.san4n.downloader;

import com.san4n.DownloadInfo;
import com.san4n.helpers.HttpHelper;

@FunctionalInterface
public interface Downloader {

    void download(DownloadInfo downloadInfo);

    static Downloader getDownloader(DownloadInfo downloadInfo){
        if (HttpHelper.isMultipartDownloadSupport(downloadInfo.getHttpURLConnection())){
            return new MultiThreadDownloader();
        }else {
            return new SingleThreadDownloader();
        }
    }
}
