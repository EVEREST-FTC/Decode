package com.everest.constants;

import com.everest.CommandBased.definition.Clock;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.concurrent.TimeUnit;

public class Constants {
    public static final double KP = 0.02;
    public static final double KI = 0.06;

    public static final double KD = 0.00001;
    //TODO: calibrar
    public static final double POWER_LAUNCHER_CONVERSION = 810;
    public static final double PID_INCREMENT_BLUE = 0.6;
    public static final double PID_INCREMENT_RED = -5;
    public static final double TARGET_HEIGHT = 0.75;
    public static final double CAMERA_HEIGHT = 0.38;
    public static final double DELTA_HEIGHT = TARGET_HEIGHT - CAMERA_HEIGHT + 0.45;
    public static final double TAG_RELATIVE_HEIGHT = TARGET_HEIGHT - CAMERA_HEIGHT;
    public static final double G = 9.81;
    public static final double MAX_HEIGHT = 0.9;



    public static final double MAX_RPM = 6000;
    public static final int Eleveitor_Reduction = 5;
    public static final int Eleveitor_tickConversion = 28 * Eleveitor_Reduction * Eleveitor_Reduction * Eleveitor_Reduction;

    private static final double tickConversion = 28;
    public static double FORWARD_TICK_CONVERSION = 60/tickConversion;
    public static double REVERSE_TICK_CONVERSION = tickConversion/60;
    public static double CONVERSION_FACTOR = 2.4;

    public static double PLATFORM_MAX_SERVO_ANGLE = 280;
    public static double GATE_MAX_SERVO_ANGLE = 280;
    public static double SARCOFOGO_MAX_SERVO_ANGLE = 280;





    public static final double INITIAL_POSITION = 0.3857
            ;

    public static final double PLATFORM_MAX_ANGLE = 60;
    public static final double PLATFORM_MIN_ANGLE = 45;

    public static final double GATE_MAX_ANGLE = 280;
    public static final double GATE_MIN_ANGLE = 0;
    public static final double SARCOFOGO_MAX_ANGLE = 280;
    public static final double SARCOFOGO_MIN_ANGLE = 0;



    public static final double leftInitialPosition = 0.99;
    public static final double rightInitialPosition = 0.01;
    public static final double GateInitialPosition = 30;

    public static final double SarcofogoInitialPosition = 0;

    public static final double targetLeftPosition = 0.89;
    public static final double targetRightPosition = 0.12;
    public static Clock clockSeconds = new ClockAdapter(new ElapsedTime(), TimeUnit.SECONDS);
    public static final double INTAKE_POWER = 0.8;

    public static final double CHASSIS_LIMIT_POWER = 1.0;
    public static final double CHASSIS_LIMIT_POWER_TURN = 0.5;


}
