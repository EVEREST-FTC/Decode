package com.everest.constants.meta;

import com.everest.constants.Constants.LauncherControllerConstants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnumTeam {
    SOLO_BLUE_FAR(0,
            -90,
            LauncherControllerConstants.PID_INCREMENT_BLUE,
            LauncherControllerConstants.PID_SHORT_INCREMENT_BLUE,
            LauncherControllerConstants.PID_LONG_INCREMENT_BLUE,
            false,
            true,
            40
            ),
    SOLO_RED_FAR(1,
            90,
            LauncherControllerConstants.PID_INCREMENT_RED,
            LauncherControllerConstants.PID_SHORT_INCREMENT_RED,
            LauncherControllerConstants.PID_LONG_INCREMENT_RED,
            false,
            true,
            10),

    SOLO_BLUE_CLOSE(0,
            -90,
            LauncherControllerConstants.PID_INCREMENT_BLUE,
            LauncherControllerConstants.PID_SHORT_INCREMENT_BLUE,
            LauncherControllerConstants.PID_LONG_INCREMENT_BLUE,
            true,
            true,
            40
    ),
    SOLO_RED_CLOSE(1,
            -90,
            LauncherControllerConstants.PID_INCREMENT_RED,
            LauncherControllerConstants.PID_SHORT_INCREMENT_RED,
            LauncherControllerConstants.PID_LONG_INCREMENT_RED,
            true,
            true,
            10
    );
    final int pipeline;
    final double offset;
    final double increment;
    final double shortIncrement;
    final double LargeIncrement;
    final boolean isClose;
    final boolean isSolo;
    final double outtakeIncrement;

}
