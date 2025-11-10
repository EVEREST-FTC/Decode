package com.everest.trigger.subsystem;

import com.everest.CommandBased.definition.CommandScheduler;
import com.everest.CommandBased.essentials.SubsystemBase;
import com.everest.trigger.Constants;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class TriggerSubsystem extends SubsystemBase {


    Servo ServoLG, ServoRG;
    Telemetry telemetry;
    public TriggerSubsystem(HardwareMap hardwareMap, Telemetry telemetry){
        ServoLG = hardwareMap.get(Servo.class,"ServoLG");
        ServoRG = hardwareMap.get(Servo.class,"ServoRG");
        resetPosiiton();

        this.telemetry = telemetry;
        CommandScheduler.getInstance().registerSubsystem(this);
    }


    public void setPositionR(double alvo){
        ServoRG.setPosition(alvo);
    }
    public void setPositionL(double alvo){
        ServoLG.setPosition(alvo);
    }


    @Override
    public void periodic() {
        telemetry.addData("posiçãoservoL", ServoLG.getPosition());
        telemetry.addData("posiçãoservoR", ServoRG.getPosition());
    }

    public void resetPosiiton(){
        setPositionL(Constants.leftInitialPosition);
        setPositionR(Constants.rightInitialPosition);
    }

}