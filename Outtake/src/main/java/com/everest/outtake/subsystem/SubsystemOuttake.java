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
    double memoreRight = 0;
    double memoreLeft = 0;
    @Getter
    private double targetVelocity = 0;

    private final RevColorSensorV3 ColorSensorL,ColorSensorR,SensorgateLeft,SensorgateRight;
    public SubsystemOuttake(HardwareMap hardwareMap, Telemetry telemetry){
        MOUTL = hardwareMap.get(DcMotorEx.class,"MOUTL");
        MOUTR = hardwareMap.get(DcMotorEx.class,"MOUTR");
        MOUTL.setDirection(DcMotorSimple.Direction.REVERSE);
        ColorSensorL = hardwareMap.get(RevColorSensorV3.class,"ColorSensorL");
        ColorSensorR = hardwareMap.get(RevColorSensorV3.class,"ColorSensorR");
        SensorgateLeft = hardwareMap.get(RevColorSensorV3.class,"SensorgateLeft");
        SensorgateRight = hardwareMap.get(RevColorSensorV3.class,"SensorgateRight");
        this.telemetry = telemetry;
        CommandScheduler.getInstance().registerSubsystem(this);
    }
    public void setVelocity(double velocity){
        velocity *= Constants.REVERSE_TICK_CONVERSION ;
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

    public boolean getDistanceLeft(){
        if (SensorgateLeft.getDistance(DistanceUnit.MM)< 60)
            memoreLeft += 1;
        return memoreLeft > 3;
    }
    public void resetmemore(){
       memoreLeft = 0;
       memoreRight = 0;
    }
    public boolean getDistanceRight(){
        if (SensorgateRight.getDistance(DistanceUnit.MM)< 30)
            memoreRight += 1;
        return memoreRight > 3;
    }

    public  int artifacts(){
       if (hasArtifact()&&getDistanceLeft()&&getDistanceRight())
           return 3;
       else if (hasArtifact()&&getDistanceRight()&&!getDistanceLeft())
           return 2;
       else if (hasArtifact()&&!getDistanceRight()&&!getDistanceLeft())
           return 1;
       else
           return 0;
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
        return distanceSensorR() < 50 || distanceSensorL() < 50;
    }


    @Override
    public void periodic() {
        telemetry.addData("outtake-velocidade", MOUTR.getVelocity());
        telemetry.addData("outtake-alvoVelociade",targetVelocity);
        telemetry.addData("outtake-atSetpoint",atSetpoint());
        telemetry.addData("outtake-hasArtifact",hasArtifact());
        telemetry.addData("artinumber",artifacts());

    }

}
