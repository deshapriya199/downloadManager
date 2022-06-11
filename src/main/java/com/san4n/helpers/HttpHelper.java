package com.san4n.helpers;

import java.net.HttpURLConnection;

public class HttpHelper {
    private HttpHelper(){}

    public static boolean isMultipartDownloadSupport(HttpURLConnection httpURLConnection){
        return true;
    }
}
