package com.everest.constants;

import com.everest.CommandBased.definition.Clock;
import com.qualcomm.robotcore.util.ElapsedTime;
import java.util.concurrent.TimeUnit;

import lombok.Getter;
import lombok.Setter;

/// Constantes genéricas do robô, e alguns métodos estáticos auxiliares
public class Constants {

    public static Clock clockSeconds = new ClockAdapter(new ElapsedTime(), TimeUnit.SECONDS);
    public static Clock robotTimer = new ClockAdapter(new ElapsedTime(), TimeUnit.SECONDS);
    @Setter
    @Getter
    private static com.everest.constants.Pattern matchPattern = Pattern.BOTTOM;
    /// Constantes do piloto]
    public static class ControllerConstants{
        public static double CHASSIS_LIMIT_POWER = 1.0;
        public static double CHASSIS_LIMIT_POWER_TURN = 0.5;
        public static final double CHASSIS_MIN_LIMIT_POWER_TURN = 0.5;


        public static final double DEAD_ZONE_MIN = 0.01;

        public static final double GAMEPAD_AIM_TRIGGER = 0.9;
    }
    ///RELATIVO AO PID da limelight
    public static class GyroConstants{
        public static final double KP = 0.02405;
        public static final double KI = 0.08;///0.0.045

        public static final double KD = 0;
        public static final double KP_TELEOP = 0.0276;
        public static final double KI_TELEOP = 0.023;///0.0.045

        public static final double KD_TELEOP = 0;
        ///
        public static final double PID_MAX = 0.5;
        public static final double iRange = 5;

        public static final double ADMISSIBLE_ERROR = 1.0;
    }
    ///Relativo ao modelo de lancador
    public static class LauncherControllerConstants{
        public static final double DISTANCE_RANGE = 1.5;
        public static final double PID_INCREMENT_BLUE = -3;

        public static final double PID_LONG_INCREMENT_BLUE = -1;
        public static final double PID_SHORT_INCREMENT_BLUE = -6;
        public static final double PID_SHORT_INCREMENT_RED = -10;

        public static final double PID_LONG_INCREMENT_RED = -5.1;

        public static final double PID_INCREMENT_RED = -6.4;
    }
    /// Constantes da camera
    public static class CameraConstants{
        public static final double TARGET_HEIGHT = 0.75;
        public static final double CAMERA_HEIGHT = 0.41;
        public static final double DELTA_HEIGHT = TARGET_HEIGHT - CAMERA_HEIGHT + 0.45;
        public static final double TAG_RELATIVE_HEIGHT = TARGET_HEIGHT - CAMERA_HEIGHT;
        public static final double G = 9.81;
        public static final double MAX_HEIGHT = 0.9;
        public static final double initialAngle = 15;
        public static final double shortIncrementDistance = 1.79;
        public static final double largeIncrementDistance = 2.6;
    }
    ///  Constantes do elevador
    public static class ElevatorConstants{

        public static final int Elevator_Reduction = 5;
        public static final int Elevator_tickConversion = 28 * Elevator_Reduction * Elevator_Reduction * Elevator_Reduction;

        private static final double tickConversion = 28;
        public static double REVERSE_TICK_CONVERSION = tickConversion/60;
    }
    /// Constantes da plataforma de lancamento
    public static class PlatformConstants{
        public static final double FAR_POWER_LAUNCHER_CONVERSION = 780.5;
        public static final double POWER_LAUNCHER_CONVERSION = 784.3; ///750
        public static final double CLOSE_POWER_LAUNCHER_CONVERSION = 840;

        public static final double MAX_RPM = 6000;
        public static double CONVERSION_FACTOR = 2.4;

        public static double PLATFORM_MAX_SERVO_ANGLE = 280;

        public static final double INITIAL_POSITION = 0.3857;

        public static final double PLATFORM_MAX_ANGLE = 55;
        public static final double PLATFORM_MIN_ANGLE = 45;
    }
    /// Constantes do gate
    public static class GateConstants{
        public static double GATE_MAX_SERVO_ANGLE = 280;

        public static final double GATE_MAX_ANGLE = 75;
        public static final double GATE_MIN_ANGLE = 0;


        public static final double GateClosePosition = 70;
        public static final double GateOpenPosition = 0;
    }
    /// Constantes da flag (bandeira)
    public static class FlagConstants{
        public static double BANDEIRA_MAX_SERVO_ANGLE = 280;

        public static final double BANDEIRA_MIN_ANGLE = 0;
        public static final double BANDEIRA_MAX_ANGLE = 90;
    }
    /// Constantes do sarcofago
    public static class SarcofagoConstants{

        public static double SARCOFOGO_MAX_SERVO_ANGLE = 280;

        public static final double SARCOFOGO_MAX_ANGLE = 90;
        public static final double SARCOFOGO_MIN_ANGLE = 0;


        public static final double SarcofogoInitialPosition = 10;
    }
    /// Constantes do trigger (gatilho)
    public static class TriggerConstants{


        public static final double rightInitialPosition = 0;

        public static final double leftInitialPosition = 1 - rightInitialPosition -0.02 ;

        public static final double targetLeftPosition = 0.79;
        public static final double targetRightPosition = 1 - targetLeftPosition ;
    }
    /// Constantes do intake
    public static class IntakeConstants{
        public static final double INTAKE_POWER = 0.02;/// 1.1
        public static final double INTAKE_POWER_CLOSE = 0.03;
        public static final double LAST_INTAKE_POWER = 0.05;
        public static final double CLOSE_LAST_INTAKE_POWER = 0.07;

        public static final int Intake_Reduction = 5;

        private static final double tickConversion = 28;
        public static double REVERSE_TICK_CONVERSION = tickConversion/Intake_Reduction;


    }

}
