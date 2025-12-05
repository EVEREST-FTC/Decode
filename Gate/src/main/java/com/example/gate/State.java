package com.example.gate;

public enum State {
    OPENED,
    CLOSED;

    public static State selector(boolean hasArtifact){
        if(hasArtifact)
            return CLOSED;
        return OPENED;
    }
}
