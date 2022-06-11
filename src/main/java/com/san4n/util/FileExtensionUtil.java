package com.san4n.util;

import com.san4n.constant.MimeType;

import java.net.HttpURLConnection;

public class FileExtensionUtil {
    public static String fileExtension(HttpURLConnection httpURLConnection) {
        String contentType = httpURLConnection.getContentType();
        String applicationType = StringUtil.applicationTypeFrom(contentType);
        MimeType mimeType = MimeType.fromValue(applicationType);
        return mimeType.extension();
    }
}
