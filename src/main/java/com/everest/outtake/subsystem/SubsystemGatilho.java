package com.everest.outtake.subsystem;

import com.everest.CommandBased.definition.CommandScheduler;
import com.everest.CommandBased.essentials.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;


public class SubsystemGatilho extends SubsystemBase {


    Servo ServoLG, ServoRG;
    Telemetry telemetry;
    public SubsystemGatilho(HardwareMap hardwareMap, Telemetry telemetry){
        ServoLG = hardwareMap.get(Servo.class,"ServoLG");
        ServoRG = hardwareMap.get(Servo.class,"ServoRG");
        ServoLG.setPosition(0.97);
        ServoRG.setPosition(0.05);

        this.telemetry = telemetry;
        telemetry.setMsTransmissionInterval(11);
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

}
