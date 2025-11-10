package com.everest.outtake.subsystem;

import com.everest.CommandBased.definition.CommandScheduler;
import com.everest.CommandBased.essentials.SubsystemBase;
import com.everest.outtake.Constants;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Subsystem extends SubsystemBase {
    DcMotorEx MOUTR, MOUTL;
    Telemetry telemetry;
    private double targetVelocity = 0;
    public Subsystem(HardwareMap hardwareMap, Telemetry telemetry){
        MOUTL = hardwareMap.get(DcMotorEx.class,"MOUTL");
        MOUTR = hardwareMap.get(DcMotorEx.class,"MOUTR");
        MOUTL.setDirection(DcMotorSimple.Direction.REVERSE);
        this.telemetry = telemetry;
        CommandScheduler.getInstance().registerSubsystem(this);
    }
    public void setVelocity(double velocity){
        velocity *= Constants.tickReverse;
        this.targetVelocity = velocity;
        MOUTR.setVelocity(-velocity);
        MOUTL.setVelocity(-velocity);
    }
    public void brake(){
        MOUTR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        MOUTL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    public double getVelocity(){
        return MOUTL.getVelocity()*Constants.tickForward;
    }
    public double getTargetVelocity(){
        return targetVelocity;
    }
    @Override
    public void periodic() {
        telemetry.addData("velocidade", getVelocity());
    }

}
