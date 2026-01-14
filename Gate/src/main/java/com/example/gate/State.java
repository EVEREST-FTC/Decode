package com.example.gate;

import com.everest.CommandBased.definition.Command;
import com.everest.constants.Constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
public enum State {
    OPENED,
    CLOSED;
    @Setter
    Command associatedCommand;

    public static State selector(boolean hasArtifact){
        if(hasArtifact)
            return CLOSED;
        return OPENED;
    }
}
