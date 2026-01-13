package com.everest.plataform.subsystem;

import static com.everest.constants.Constants.PLATFORM_MAX_ANGLE;
import static com.everest.constants.Constants.PLATFORM_MIN_ANGLE;

import com.everest.CommandBased.definition.CommandScheduler;
import com.everest.CommandBased.essentials.SubsystemBase;
import com.everest.constants.Constants;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
//NAO meXeEr
public final class SubsystemCalibrator extends SubsystemBase {
    Servo ServoRC, ServoLC;
    Telemetry telemetry;
    public SubsystemCalibrator(HardwareMap hardwareMap, Telemetry telemetry){
        ServoRC = hardwareMap.get(Servo.class,"ServoLC");
        ServoLC = hardwareMap.get(Servo.class,"ServoRC");
        this.telemetry = telemetry;

        ServoLC.setDirection(Servo.Direction.REVERSE);

        setPositionL(Constants.INITIAL_POSITION);
        /// 0.3875 é a posição inicial proporcional a 45 graus do servoRC
        /// 0.6143 é a posição inicial proporcional a 45 graus do servoLC


        CommandScheduler.getInstance().registerSubsystem(this);

    }
    public void setPositionL(double alvo){
        double treatedAngle = limiter(alvo);
        treatedAngle = (treatedAngle/ Constants.PLATFORM_MAX_SERVO_ANGLE)* Constants.CONVERSION_FACTOR;
        ServoRC.setPosition(treatedAngle);
        ServoLC.setPosition(treatedAngle);

    }

    private double limiter(double angle){
        if(angle> PLATFORM_MAX_ANGLE)
            return PLATFORM_MAX_ANGLE;
        else return Math.max(angle, PLATFORM_MIN_ANGLE);
    }

}
