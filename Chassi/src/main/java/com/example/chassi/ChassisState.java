package com.example.chassi;

public enum ChassisState {
    DRIVING,
    AIMING;
    public static ChassisState selector(boolean isSeeking){
        if(isSeeking)
            return AIMING;
        return DRIVING;
    }

}
