package com.everest.outtake.subsystem;

import com.everest.CommandBased.definition.CommandScheduler;
import com.everest.CommandBased.essentials.SubsystemBase;
import com.everest.constants.Constants.ElevatorConstants;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import lombok.Getter;
import lombok.Setter;

public class SubsystemOuttake extends SubsystemBase {
    DcMotorEx MOUTR, MOUTL;
    Telemetry telemetry;
    double memoreRight = 0;
    double memoreLeft = 0;

    double memoreouttake = 0;
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
        velocity *= ElevatorConstants.REVERSE_TICK_CONVERSION ;
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
        if (SensorgateLeft.getDistance(DistanceUnit.MM)< 28)
            memoreLeft += 1;
        return memoreLeft > 4;
        /*return SensorgateLeft.getDistance(DistanceUnit.MM)< 35;*/
    }
    public boolean intakeleftdistance(){
        return SensorgateLeft.getDistance(DistanceUnit.MM)< 28;
    }

    public void resetmemore(){
       memoreLeft = 0;
       memoreRight = 0;
       memoreouttake = 0;
    }
    public boolean getDistanceRight(){
        if (SensorgateRight.getDistance(DistanceUnit.MM)< 28)
            memoreRight += 1;
        return memoreRight > 4;
        /*return SensorgateRight.getDistance(DistanceUnit.MM)< 35;*/
    }

    public  int artifacts(){
        int left = getDistanceLeft()?1:0;
        int right = getDistanceRight()?1:0;
        int center = hasArtifact()?1:0;
        return left+right+center;
    }
    public boolean artifactCount(){
        return artifacts()>2;

    }

    public void brake(){
        MOUTR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        MOUTL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    private boolean rightSetpoint(){
        double velocity = Math.abs(MOUTR.getVelocity());
        if (velocity == 0 || targetVelocity == 0)
            return false;
        return Math.abs(velocity-targetVelocity)<50;
    }
    private boolean leftSetpoint(){
        double velocity =  Math.abs(MOUTL.getVelocity());
        if (velocity == 0 || targetVelocity == 0)
            return false;
        return Math.abs(velocity-targetVelocity)<50;
    }
    public boolean atSetpoint(){
        return rightSetpoint()&&leftSetpoint();
    }

    public boolean hasArtifact(){
        return distanceSensorR() < 50 || distanceSensorL() < 50;
    }

    @Override
    public void periodic() {

        telemetry.addData("artifacts in robot", artifacts());
        telemetry.addData("intake left sensor", intakeleftdistance());

    }

}
