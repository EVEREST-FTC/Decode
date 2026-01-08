package com.example.sarcofogo;

import static com.everest.constants.Constants.BANDEIRA_MAX_SERVO_ANGLE;
import static com.everest.constants.Constants.GATE_MAX_ANGLE;
import static com.everest.constants.Constants.GATE_MAX_SERVO_ANGLE;
import static com.everest.constants.Constants.GATE_MIN_ANGLE;
import static com.everest.constants.Constants.SARCOFOGO_MAX_ANGLE;
import static com.everest.constants.Constants.SARCOFOGO_MAX_SERVO_ANGLE;
import static com.everest.constants.Constants.SARCOFOGO_MIN_ANGLE;

import com.everest.CommandBased.definition.CommandScheduler;
import com.everest.CommandBased.essentials.SubsystemBase;
import com.everest.constants.Constants;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class SubsystemSarcofogo extends SubsystemBase {


    Servo ServoSarcofogo,Servobandeira;
    Telemetry telemetry;
    double position;
    public SubsystemSarcofogo(HardwareMap hardwareMap, Telemetry telemetry){
        ServoSarcofogo = hardwareMap.get(Servo.class,"ServoSarcofogo");
        Servobandeira = hardwareMap.get(Servo.class,"ServoBandeira");
        resetPosiiton();
        Servobandeira.setPosition(1);

        this.telemetry = telemetry;
        CommandScheduler.getInstance().registerSubsystem(this);
    }


    public void setPositionGate(double alvo){
        position = limiter(alvo)/SARCOFOGO_MAX_SERVO_ANGLE;
        ServoSarcofogo.setPosition(1 - position);
    }
    public void setPositionbandeira(double alvo){
        position = limiter(alvo)/BANDEIRA_MAX_SERVO_ANGLE;
        Servobandeira.setPosition(1 -position);
    }


    @Override
    public void periodic() {


    }
    public void resetPosiiton(){
        setPositionGate(Constants.SarcofogoInitialPosition);
    }
    private double limiter(double angle){
        if(angle> SARCOFOGO_MAX_ANGLE)
            return SARCOFOGO_MAX_ANGLE;
        else return Math.max(angle,SARCOFOGO_MIN_ANGLE);
    }
    private double limiterB(double angle){
        if(angle> Constants.BANDEIRA_MAX_ANGLE)
            return Constants.BANDEIRA_MAX_ANGLE;
        else return Math.max(angle, Constants.BANDEIRA_MIN_ANGLE);
    }

}