package com.example.gate;

import com.everest.CommandBased.definition.Command;
import com.everest.constants.Constants;
import com.everest.constants.Pattern;

import org.firstinspires.ftc.robotcore.external.Const;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
public enum State {
    OPENED,
    CLOSED,
    BOTTOM_SELECTION;
    @Setter
    Command associatedCommand;
    /*public static State selector(boolean hasArtifact){
        if(hasArtifact)
            return CLOSED;
        return OPENED;
    }*/

    public static State selector(boolean hasArtifact, boolean isSending, boolean isUnactive){
        if(Constants.getMatchPattern().equals(Pattern.BOTTOM)&&
                !isSending
                &&
                !isUnactive)
            return BOTTOM_SELECTION;
        else if(hasArtifact)
            return CLOSED;
        else
        return OPENED;
    }
}
