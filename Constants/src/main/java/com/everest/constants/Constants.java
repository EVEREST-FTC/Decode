package com.everest.constants;

import com.acmerobotics.roadrunner.Pose2d;
import com.everest.CommandBased.definition.Clock;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

import java.util.concurrent.TimeUnit;

import lombok.Getter;
import lombok.Setter;

/// Constantes genéricas do robô, e alguns métodos estáticos auxiliares
public class Constants {


    public static Clock clockSeconds = new ClockAdapter(new ElapsedTime(), TimeUnit.SECONDS);
    public static Clock robotTimer = new ClockAdapter(new ElapsedTime(), TimeUnit.SECONDS);
    public static class AutoConstants{
        @Setter
        @Getter
        private static com.everest.constants.Pattern matchPattern = Pattern.BOTTOM;

        @Setter
        @Getter
        private static Pose2d autonomousFinalPose = new Pose2d(0, 0, 0);

        public static final Pose2d aimingBlue = new Pose2d(0, 0, 0);
        public static final Pose2d aimingRed = new Pose2d(0, 0, 0);
    }

    /// Constantes do piloto]
    public static class ControllerConstants{
        public static double CHASSIS_LIMIT_POWER = 1.0;
        public static double CHASSIS_REDUCTION = CHASSIS_LIMIT_POWER / 2;
        public static double CHASSIS_LIMIT_POWER_TURN = 0.5;
        public static final double CHASSIS_MIN_LIMIT_POWER_TURN = 0.5;


        public static final double DEAD_ZONE_MIN = 0.01;

        public static final double GAMEPAD_AIM_TRIGGER = 0.7;
    }
    ///RELATIVO AO PID do chassi com fornecimento da limelight
    public static class GyroConstants{
        public static final double KP = 0.015;///0.02
        public static final double KI =  0.032;///0.0.035

        public static final double KD = 0.002;
        public static final double KP_TELEOP = 0.018; ///0.0267 ,  0.018
        public static final double KI_TELEOP = 0.035;///0.0.035 , 0.0040

        public static final double KD_TELEOP = 0.0018;///0.0015 , 0.0018
        ///
        public static final double PID_MAX = 0.5;
        public static final double iRange = 10;

        public static final double ADMISSIBLE_ERROR = 1.4;

        public static final double SHORT_ADMISSIBLE_ERROR = 2.5;
    }
    ///Relativo ao modelo de lancador
    public static class LauncherControllerConstants{
        public static final double DISTANCE_RANGE = 1.5;
        public static final double PID_INCREMENT_BLUE = -1.5;//-1.5

        public static final double PID_LONG_INCREMENT_BLUE = -2;//-2
        public static final double PID_SHORT_INCREMENT_BLUE =  -6;//-6
        public static final double PID_SHORT_INCREMENT_RED = -10;

        public static final double PID_LONG_INCREMENT_RED = -5.1;

        public static final double PID_INCREMENT_RED = -6.4;
    }
    public static class OuttakeConstants{
        public static final double MAX_MEMORE_CONT = 7;
        public static final double ACTIVE_MIN_CONT_LEFT_SENSOR = 34;
        public static final double ACTIVE_MIN_CONT_RIGHT_SENSOR = 30;
        public static final double ACTIVE_MIN_CONT_OUT_SENSOR = 55;

        public static final double MAX_VELOCITY = 5500;

        public static final double ADMISSIBLE_ERROR = 40;

        public static final double admissibleSeconds = 0.2;

        public static final double AutoAdmissibleSeconds = 12;
       ;

    }
    /// Constantes da camera
    public static class CameraConstants{
        public static final double TARGET_HEIGHT = 0.75;
        public static final double CAMERA_HEIGHT = 0.41;
        public static final double DELTA_HEIGHT = TARGET_HEIGHT - CAMERA_HEIGHT + 0.45;
        public static final double TAG_RELATIVE_HEIGHT = TARGET_HEIGHT - CAMERA_HEIGHT;
        public static final double G = 9.81;
        /// Define a altura maxima do projetil
        public static final double MAX_HEIGHT = 0.8; ///0.8
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
        public static final double FAR_POWER_LAUNCHER_CONVERSION = 715; /// 690
        public static final double POWER_LAUNCHER_CONVERSION = 715; /// 735
        public static final double CLOSE_POWER_LAUNCHER_CONVERSION = 834;///820

        public static final double MAX_RPM = 6000;
        public static double CONVERSION_FACTOR = 2.4;

        public static double PLATFORM_MAX_SERVO_ANGLE = 280;

        public static final double INITIAL_POSITION = 0.3857;

        public static final double PLATFORM_MAX_ANGLE = 60;
        public static final double PLATFORM_MIN_ANGLE = 32;
    }
    /// Constantes do gate
    public static class GateConstants{
        public static final double GATE_OPEN_POWER = 1; ///0.8
        public static final double GATE_CLOSE_POWER = 0;
        public static final double GATE_SARCOFOGO_POWER = -0.3;
    }
    /// Constantes da flag (bandeira)
    public static class FlagConstants{
        public static double BANDEIRA_MAX_SERVO_ANGLE = 280;

        public static final double BANDEIRA_MIN_ANGLE = 0;
        public static final double BANDEIRA_MAX_ANGLE = 135;
    }
    /// Constantes do sarcofago
    public static class SarcofagoConstants{

        public static double SARCOFOGO_MAX_SERVO_ANGLE = 280;

        public static final double SARCOFOGO_MAX_ANGLE = 100;
        public static final double SARCOFOGO_MIN_ANGLE = 0;
        public static final double SARCOPHAGI_SEND_POSITION = 80;


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
        public static final double INTAKE_POWER_NORMAL = 0.040; ///0.04

        public static final double INTAKE_POWER_L= 0.008;/// 0.025
        public static final double INTAKE_POWER_CLOSE = 0.03;
        public static final double LAST_INTAKE_POWER = 0.05;/// 0.05
        public static final double CLOSE_LAST_INTAKE_POWER = 0.07;

        public static final int Intake_Reduction = 5;

        private static final double tickConversion = 28;
        public static double REVERSE_TICK_CONVERSION = tickConversion/Intake_Reduction;


    }

}
