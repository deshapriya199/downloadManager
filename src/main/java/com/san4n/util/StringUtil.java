package com.san4n.util;

import java.util.Arrays;

public class StringUtil {

    public static final String EMPTY = "";

    private StringUtil(){}

    public static String applicationTypeFrom(String contentType){
        String [] arr = contentType.split(";|:");
        return arr[0].trim();
    }
}
