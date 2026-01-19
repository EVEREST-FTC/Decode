package com.example.sarcofogo;

public enum Moment {
    SEND,
    KEEP,
    UNACTIVE;
    public static Moment select(boolean artifactListener){
        if(artifactListener)
            return SEND;
        return KEEP;
    }
}
