package com.san4n.constant;

public enum MimeType {
    TEXT_HTML("text/html", ".html"),
    APPLICATION_ZIP("application/zip", ".zip"),
    APPLICATION_X_GZIP("application/x-gzip", ".tgz"),
    APPLICATION_OCTET_STREAM("application/octet-stream", ".msi"),
    BINARY_OCTET_STREAM("binary/octet-stream", ".exe"),
    AUDIO_MPEG("audio/mpeg", ".mp3"),
    APPLICATION_X_MSDOS_PROGRAM("application/x-msdos-program", ".exe"),
    ;

    private String value;
    private String extension;

    MimeType(String value, String extension) {
        this.value = value;
        this.extension = extension;
    }

    public String value(){
        return value;
    }

    public String extension(){
        return extension;
    }

    public static MimeType fromValue(String value){
        for(MimeType mimeType : MimeType.values()){
            if (mimeType.value().equals(value)){
                return mimeType;
            }
        }
        System.out.printf("please add %s to the Mimetype\n", value);
        return null;
    }
}
