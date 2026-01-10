package com.everest.constants.meta;

import com.everest.constants.Constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnumTeam {
    SOLO_BLUE_FAR(0,
            -90,
            Constants.PID_INCREMENT_BLUE,
            Constants.PID_SHORTINCREMENT_BLUE,
            false,
            true
            ),
    SOLO_RED_FAR(1,
            90,
            Constants.PID_INCREMENT_RED,
            Constants.PID_SHORTINCREMENT_RED,
            false,
            true);
    final int pipeline;
    final double offset;
    final double increment;
    final double shortIncrement;
    final boolean isClose;
    final boolean isSolo;

}
