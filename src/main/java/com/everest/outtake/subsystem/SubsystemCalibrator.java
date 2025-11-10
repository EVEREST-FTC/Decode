package com.everest.outtake.subsystem;

import com.everest.CommandBased.definition.CommandScheduler;
import com.everest.CommandBased.essentials.SubsystemBase;
import com.everest.outtake.Constants;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;


public class SubsystemCalibrator extends SubsystemBase {
    Servo ServoRC, ServoLC;
    Telemetry telemetry;
    public SubsystemCalibrator(HardwareMap hardwareMap, Telemetry telemetry){
        ServoRC = hardwareMap.get(Servo.class,"ServoLC");
        ServoRC.setPosition(0.3857); /// 0.3875 é a posição inicial proporcional a 45 graus do servoRC
        ServoLC = hardwareMap.get(Servo.class,"ServoRC");
        ServoLC.setPosition(0.6143); /// 0.6143 é a posição inicial proporcional a 45 graus do servoLC
        ServoLC.setDirection(Servo.Direction.REVERSE);

        this.telemetry = telemetry;
        telemetry.setMsTransmissionInterval(11);
        CommandScheduler.getInstance().registerSubsystem(this);
    }
    public void setPositionL(double alvo){
        alvo = (alvo/Constants.MaxangleServoCalibrator)* Constants.ConversionFactoreCalibrator;
        ServoRC.setPosition(alvo);
        ServoLC.setPosition(alvo);

    }

    @Override
    public void periodic() {
        telemetry.addData("servoCalibradorL", ServoRC.getPosition());
        telemetry.addData("servoCalibradorR", ServoLC.getPosition());


    }
}
