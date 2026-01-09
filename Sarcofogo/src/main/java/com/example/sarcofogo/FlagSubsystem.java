package com.example.sarcofogo;

import static com.everest.constants.Constants.BANDEIRA_MAX_SERVO_ANGLE;
import static com.everest.constants.Constants.SARCOFOGO_MAX_ANGLE;
import static com.everest.constants.Constants.SARCOFOGO_MAX_SERVO_ANGLE;
import static com.everest.constants.Constants.SARCOFOGO_MIN_ANGLE;

import com.everest.CommandBased.definition.CommandScheduler;
import com.everest.CommandBased.essentials.SubsystemBase;
import com.everest.constants.Constants;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class FlagSubsystem extends SubsystemBase {

    Servo Servobandeira;
    double position;
    public FlagSubsystem(HardwareMap hardwareMap, Telemetry telemetry){
        Servobandeira = hardwareMap.get(Servo.class,"ServoBandeira");
        Servobandeira.setPosition(1);
    }


    public void setPositionbandeira(double alvo){
        position = limiterB(alvo)/BANDEIRA_MAX_SERVO_ANGLE;
        Servobandeira.setPosition(1 -position);
    }

    private double limiterB(double angle){
        if(angle> Constants.BANDEIRA_MAX_ANGLE)
            return Constants.BANDEIRA_MAX_ANGLE;
        else return Math.max(angle, Constants.BANDEIRA_MIN_ANGLE);
    }
}
