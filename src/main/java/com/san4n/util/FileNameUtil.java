package com.san4n.util;

import com.san4n.property.ApplicationProperty;

import java.net.HttpURLConnection;
import java.util.Objects;

public class FileNameUtil {

    private FileNameUtil(){}

    public static String fileName(String downloadUrl, String contentDisposition, String fileExtension){

        String fileName = null;

        if(Objects.isNull(contentDisposition)){
            fileName = fileNameFromUrl(downloadUrl, fileExtension);
        }else{
            contentDisposition = contentDisposition.trim();
            fileName = fileNameFromContentDisposition(downloadUrl, contentDisposition, fileExtension);
        }

        if(Objects.isNull(fileName)){
            fileName = String.valueOf(System.currentTimeMillis());
        }
        return fileName;
    }

    private static String fileNameFromContentDisposition(String downloadUrl, String contentDisposition, String fileExtension) {
        if(contentDisposition.contains("attachment;")){
            return contentDisposition.split("attachment; | filename=\"")[0];
        }else{
            return fileNameFromUrl(downloadUrl, fileExtension);
        }
    }

    private static String fileNameFromUrl(String downloadUrl, String fileExtension) {
        if(downloadUrl.contains(fileExtension)){
            String [] arr = downloadUrl.split("/");
            return arr[arr.length-1].replace(fileExtension, "");
        }
        return null;
    }

    public static String fileName(String downloadUrl, HttpURLConnection httpURLConnection, String fileExtension) {
        String contentDisposition = httpURLConnection.getHeaderField(ApplicationProperty.CONTENT_DISPOSITION);
        return FileNameUtil.fileName(downloadUrl, contentDisposition, fileExtension);
    }
}
