package com.everest.constants.meta;

import com.everest.constants.Constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnumTeam {
    BLUE(0,-90, Constants.PID_INCREMENT_BLUE,Constants.PID_SHORTINCREMENT_BLUE),
    RED(1,90, Constants.PID_INCREMENT_RED,Constants.PID_SHORTINCREMENT_RED);
    final int pipeline;
    final double offset;
    final double increment;

    final double shortIncrement;

}
