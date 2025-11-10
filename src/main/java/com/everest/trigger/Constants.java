package com.everest.trigger;

import com.everest.CommandBased.definition.Clock;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.concurrent.TimeUnit;

public class Constants {

    public static final double leftInitialPosition = 0.97;
    public static final double rightInitialPosition = 0.05;

    public static final double targetLeftPosition = 0.89;
    public static final double targetRightPosition = 0.15;
    public static Clock clockSeconds = new ClockAdapter(new ElapsedTime(), TimeUnit.SECONDS);
}
