package com.example.sarcofogo;

import static com.everest.constants.Constants.GATE_MAX_ANGLE;
import static com.everest.constants.Constants.GATE_MAX_SERVO_ANGLE;
import static com.everest.constants.Constants.GATE_MIN_ANGLE;

import com.everest.CommandBased.definition.CommandScheduler;
import com.everest.CommandBased.essentials.SubsystemBase;
import com.everest.constants.Constants;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class SubsystemSarcofogo extends SubsystemBase {


    Servo ServoSarcofogo ;
    Telemetry telemetry;
    double position;
    public SubsystemSarcofogo(HardwareMap hardwareMap, Telemetry telemetry){
        ServoSarcofogo = hardwareMap.get(Servo.class,"ServoSarcofogo");
        resetPosiiton();

        this.telemetry = telemetry;
        CommandScheduler.getInstance().registerSubsystem(this);
    }


    public void setPositionGate(double alvo){
        position = limiter(alvo)/GATE_MAX_SERVO_ANGLE;
        ServoSarcofogo.setPosition(position);
    }


    @Override
    public void periodic() {


    }

    public void resetPosiiton(){
        setPositionGate(Constants.SarcofogoInitialPosition);
    }
    private double limiter(double angle){
        if(angle> GATE_MAX_ANGLE)
            return GATE_MAX_ANGLE;
        else return Math.max(angle,GATE_MIN_ANGLE);
    }

}