package com.example.chassi;

import com.everest.CommandBased.definition.Command;
import com.everest.constants.meta.State;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum ChassisState implements State {
    DRIVING,
    AIMING;


    @Setter
    Command associatedCommand;
    public static ChassisState selector(boolean isSeeking){
        if(isSeeking)
            return AIMING;
        return DRIVING;
    }

}
