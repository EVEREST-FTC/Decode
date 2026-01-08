package com.everest.constants;

import com.everest.CommandBased.definition.Clock;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.concurrent.TimeUnit;

public class Constants {
    //RELATIVO AO PID da limelight
    public static final double KP = 0.0225;
    public static final double KI = 0.04;///0.0.025

    public static final double KD = 0;///0.000009
    ///
    public static final double PID_MAX = 0.5;
    public static final double iRange = 7;

    public static final double ADMISSIBLE_ERROR = 1.0;
    //Relativo ao modelo de lancador
    public static final double POWER_LAUNCHER_CONVERSION = 773; ///750
    public static final double CLOSE_POWER_LAUNCHER_CONVERSION = 850;
    public static final double DISTANCE_RANGE = 1.5;
    public static final double PID_INCREMENT_BLUE = -2;
    public static final double PID_SHORTINCREMENT_BLUE = -6;
    public static final double PID_SHORTINCREMENT_RED = -10;
    public static final double PID_INCREMENT_RED = -5;
    public static final double TARGET_HEIGHT = 0.75;
    public static final double CAMERA_HEIGHT = 0.41;
    public static final double DELTA_HEIGHT = TARGET_HEIGHT - CAMERA_HEIGHT + 0.45;
    public static final double TAG_RELATIVE_HEIGHT = TARGET_HEIGHT - CAMERA_HEIGHT;
    public static final double G = 9.81;
    public static final double MAX_HEIGHT = 0.9;
    public static final double initialAngle = 23;


    public static final double DEAD_ZONE_MIN = 0.05;
    public static final double MAX_RPM = 6000;
    public static final int Eleveitor_Reduction = 5;
    public static final int Eleveitor_tickConversion = 28 * Eleveitor_Reduction * Eleveitor_Reduction * Eleveitor_Reduction;

    private static final double tickConversion = 28;
    public static double FORWARD_TICK_CONVERSION = 60/tickConversion;
    public static double REVERSE_TICK_CONVERSION = tickConversion/60;
    public static double CONVERSION_FACTOR = 2.4;

    public static double PLATFORM_MAX_SERVO_ANGLE = 280;
    public static double GATE_MAX_SERVO_ANGLE = 280;
    public static double BANDEIRA_MAX_SERVO_ANGLE = 280;
    public static double SARCOFOGO_MAX_SERVO_ANGLE = 280;



    public static final double INITIAL_POSITION = 0.3857;

    public static final double PLATFORM_MAX_ANGLE = 68;
    public static final double PLATFORM_MIN_ANGLE = 47;

    public static final double GATE_MAX_ANGLE = 75;
    public static final double GATE_MIN_ANGLE = 0;

    public static final double SARCOFOGO_MAX_ANGLE = 90;
    public static final double SARCOFOGO_MIN_ANGLE = 0;

    public static final double BANDEIRA_MIN_ANGLE = 0;
    public static final double BANDEIRA_MAX_ANGLE = 90;

    public static final double rightInitialPosition = 0;

    public static final double leftInitialPosition = 1 - rightInitialPosition -0.02 ;

    public static final double GateClosePosition = 66;
    public static final double GateOpenPosition = 0;

    public static final double SarcofogoInitialPosition = 10;
    public static final double BandeiraInitialPosition = 0;

    public static final double targetLeftPosition = 0.79;
    public static final double targetRightPosition = 1 - targetLeftPosition;
    public static Clock clockSeconds = new ClockAdapter(new ElapsedTime(), TimeUnit.SECONDS);
    public static final double INTAKE_POWER = 0.8;

    public static final double CHASSIS_LIMIT_POWER = 1.0;
    public static final double CHASSIS_LIMIT_POWER_TURN = 0.5;


}
