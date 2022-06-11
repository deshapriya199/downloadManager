package com.san4n;

import com.san4n.util.MessageProperty;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        MessageProperty.welcome();

        Scanner input = new Scanner(System.in);

        MessageProperty.askForUrl();

        String url = input.nextLine();
        long startTime = System.currentTimeMillis();

        DownloadManager downloadManager = new DownloadManager(url.trim());
        downloadManager.start();

        long endTime = System.currentTimeMillis();
        MessageProperty.timeTaken((endTime-startTime)/1000);
        MessageProperty.downloadCompleted();
    }
}
