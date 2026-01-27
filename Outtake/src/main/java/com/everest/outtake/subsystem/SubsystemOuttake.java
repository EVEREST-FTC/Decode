package com.everest.outtake.subsystem;

import static com.everest.constants.Constants.robotTimer;

import com.everest.CommandBased.definition.CommandScheduler;
import com.everest.CommandBased.essentials.SubsystemBase;
import com.everest.constants.Constants;
import com.everest.constants.Constants.ElevatorConstants;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Const;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import lombok.Getter;

public class SubsystemOuttake extends SubsystemBase {
    private final DcMotorEx rightEngine, leftEngine;
    private final Telemetry telemetry;
    private double memoryRight = 0;
    private double memoryLeft = 0;
    @Getter
    private double targetVelocity = 0;

    private double lastSeenRight = 0,

    lastSeenLeft = 0;

    boolean lastTimeLaunch = false;

    int timeLaunch = 0;

    double admissibleSeconds = 0.7;
    double lastTimeCurrent = 0;
    double lastCurrent = 0;



    private final RevColorSensorV3 ColorSensorL, ColorSensorR, sensorGateLeft, sensorGateRight;
    public SubsystemOuttake(HardwareMap hardwareMap, Telemetry telemetry){
        leftEngine = hardwareMap.get(DcMotorEx.class,"MOUTL");
        rightEngine = hardwareMap.get(DcMotorEx.class,"MOUTR");
        leftEngine.setDirection(DcMotorSimple.Direction.REVERSE);
        ColorSensorL = hardwareMap.get(RevColorSensorV3.class,"ColorSensorL");
        ColorSensorR = hardwareMap.get(RevColorSensorV3.class,"ColorSensorR");
        sensorGateLeft = hardwareMap.get(RevColorSensorV3.class,"SensorgateLeft");
        sensorGateRight = hardwareMap.get(RevColorSensorV3.class,"SensorgateRight");
        this.telemetry = telemetry;
        leftEngine.setVelocityPIDFCoefficients(10, 6, 0, 0);
        rightEngine.setVelocityPIDFCoefficients(10, 6, 0, 0);
        CommandScheduler.getInstance().registerSubsystem(this);
    }
    public void setVelocity(double velocity){
        velocity *= ElevatorConstants.REVERSE_TICK_CONVERSION ;
        targetVelocity = velocity;
        rightEngine.setVelocity(-velocity);
        leftEngine.setVelocity(-velocity);
    }
    public double distanceSensorL(){
        return (ColorSensorL.getDistance(DistanceUnit.MM));
    }
    public double distanceSensorR(){
        return (ColorSensorR.getDistance(DistanceUnit.MM));
    }

    public void telemetri(float nome, float texto ){

    }

    public boolean getDistanceLeft(){
        if (sensorGateLeft.getDistance(DistanceUnit.MM)< 34) {
            memoryLeft++;
            lastSeenLeft = robotTimer.getTime();
        }
        double deltaT = robotTimer.getTime()-lastSeenLeft;
        if(deltaT>admissibleSeconds) memoryLeft = 0;
        return memoryLeft > 8;
    }
    public boolean noDebounceLeft(){
        if (sensorGateLeft.getDistance(DistanceUnit.MM)< 34) {
            memoryLeft++;
        }
        return memoryLeft > 8;
    }

    public boolean getDistanceRight(){
        if (sensorGateRight.getDistance(DistanceUnit.MM)< 34) {
            memoryRight++;
            lastSeenRight = robotTimer.getTime();
        }
        double deltaT = robotTimer.getTime()-lastSeenRight;
        if(deltaT>admissibleSeconds) memoryRight = 0;
        return memoryRight > 5;
    }
    public boolean noDebounceRight(){
        if (sensorGateRight.getDistance(DistanceUnit.MM)< 34) {
            memoryRight++;
        }
        return memoryRight > 8;
    }

    public  int artifacts(){
        int left = getDistanceLeft()?1:0;
        int right = getDistanceRight()?1:0;
        int center = hasArtifact()?1:0;
        return left+right+center;
    }
    public int noDebounceArtifacts(){
        int left = noDebounceLeft()?1:0;
        int right = noDebounceRight()?1:0;
        int center = hasArtifact()?1:0;
        return left+right+center;
    }

    public int newTimeLaunch() {
        if (!hasArtifact() && lastTimeLaunch){
            timeLaunch ++;
        }
        lastTimeLaunch = hasArtifact();
        return timeLaunch;
    }
    public void resetTimeLaunch(){
        timeLaunch = 0;
    }

    public void brake(){
        rightEngine.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftEngine.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    private boolean rightSetpoint(){
        double velocity = Math.abs(rightEngine.getVelocity());
        if (velocity == 0 || targetVelocity == 0)
            return false;
        return Math.abs(velocity-targetVelocity)<20;
    }
    private boolean leftSetpoint(){
        double velocity =  Math.abs(leftEngine.getVelocity());
        if (velocity == 0 || targetVelocity == 0)
            return false;
        return Math.abs(velocity-targetVelocity)<20;
    }

    public boolean newintakemomente(){
        return newTimeLaunch() >= 2;
    }
    public boolean oneSent(){ return newTimeLaunch() >=1;}
    public boolean atSetpoint(){
        return rightSetpoint()&&leftSetpoint();
    }

    public boolean hasArtifact(){
        return distanceSensorR() < 55 || distanceSensorL() < 55;
    }
    @Override
    public void periodic() {
        telemetry.addData("artifacts",artifacts());
        telemetry.addData("hasArtifact",hasArtifact());
        telemetry.addData("contagem",newTimeLaunch());
        telemetry.addData("intakemomente",newintakemomente());
        telemetry.addData("motor current", (leftEngine.getCurrent(CurrentUnit.MILLIAMPS)-lastCurrent)/(robotTimer.getTime())-lastTimeCurrent);
        lastCurrent = leftEngine.getCurrent(CurrentUnit.MILLIAMPS);
        lastTimeCurrent = robotTimer.getTime();

    }

}
