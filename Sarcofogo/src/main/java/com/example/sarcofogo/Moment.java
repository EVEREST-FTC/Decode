package com.example.sarcofogo;

public enum Moment {
    SEND,
    KEEP,
    UNACTIVE,
    ACTIVE;
    public static Moment select(boolean artifactListener){
        if(artifactListener)
            return SEND;
        return KEEP;
    }
}
