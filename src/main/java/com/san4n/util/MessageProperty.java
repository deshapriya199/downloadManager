package com.san4n.util;

public class MessageProperty {

    private MessageProperty(){}

    public static String WELCOME = "Welcome...\n";
    public static String ASK_PROVIDE_URL = "Please enter the url\n" +
            "URL :";
    public static String DOWNLOADING_PROGRESS = "Donwloaded : %s \r";
    public static String DOWNLOADED_TIME = "\n%d seconds time was taken \n";

    public static String DOWNLOADING_COMPLETED = "Download is completed ....";

    public static void welcome() {
        print(WELCOME);
    }

    public static void askForUrl() {
        print(ASK_PROVIDE_URL);
    }

    public static void timeTaken(long timeInSecond){
        print(DOWNLOADED_TIME, String.valueOf(timeInSecond));
    };

    public static void downloadCompleted(){
        print(DOWNLOADING_COMPLETED);
    };


    public static void print(String message, String... args){
        System.out.printf(message, args);
    }

    public static void print(String message){
        System.out.printf(message);
    }
}
