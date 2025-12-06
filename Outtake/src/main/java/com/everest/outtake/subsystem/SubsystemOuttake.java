package com.everest.outtake.subsystem;

import com.everest.CommandBased.definition.CommandScheduler;
import com.everest.CommandBased.essentials.SubsystemBase;
import com.everest.constants.Constants;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import lombok.Getter;

public class SubsystemOuttake extends SubsystemBase {
    DcMotorEx MOUTR, MOUTL;
    Telemetry telemetry;
    double artifactIn = 1;
    @Getter
    private double targetVelocity = 0;

    private final RevColorSensorV3 ColorSensorL,ColorSensorR;
    public SubsystemOuttake(HardwareMap hardwareMap, Telemetry telemetry){
        MOUTL = hardwareMap.get(DcMotorEx.class,"MOUTL");
        MOUTR = hardwareMap.get(DcMotorEx.class,"MOUTR");
        MOUTL.setDirection(DcMotorSimple.Direction.REVERSE);
        ColorSensorL = hardwareMap.get(RevColorSensorV3.class,"ColorSensorL");
        ColorSensorR = hardwareMap.get(RevColorSensorV3.class,"ColorSensorR");
        this.telemetry = telemetry;
        CommandScheduler.getInstance().registerSubsystem(this);
    }
    public void setVelocity(double velocity){
        velocity *= Constants.REVERSE_TICK_CONVERSION * artifactIn;
        this.targetVelocity = velocity;
        MOUTR.setVelocity(-velocity);
        MOUTL.setVelocity(-velocity);
    }
    public double distanceSensorL(){
        return (ColorSensorL.getDistance(DistanceUnit.MM));
    }
    public double distanceSensorR(){
        return (ColorSensorR.getDistance(DistanceUnit.MM));
    }

    public void brake(){
        MOUTR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        MOUTL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    public double getVelocity(){
        return MOUTR.getVelocity()*Constants.FORWARD_TICK_CONVERSION;
    }
    public boolean atSetpoint(){
        double velocity = -MOUTR.getVelocity();
        if (velocity == 0 || targetVelocity == 0)
            return false;
        return Math.abs(velocity-targetVelocity)<20;

    }

    public boolean hasArtifact(){
        return distanceSensorR() < 70 || distanceSensorL() < 70;
    }


    @Override
    public void periodic() {
        telemetry.addData("velocidade", getVelocity());
        telemetry.addData("velocidade", MOUTL.getVelocity()*Constants.FORWARD_TICK_CONVERSION);
        telemetry.addData("alvoVelociade",targetVelocity);
        telemetry.addData("atSetpoint",atSetpoint());
        telemetry.addData("hasArtifact",hasArtifact());

    }

}
