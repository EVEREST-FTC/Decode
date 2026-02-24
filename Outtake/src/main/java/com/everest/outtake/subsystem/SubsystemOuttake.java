package com.everest.outtake.subsystem;

import static com.everest.constants.Constants.robotTimer;

import androidx.annotation.NonNull;

import com.everest.CommandBased.definition.Command;
import com.everest.CommandBased.definition.CommandScheduler;
import com.everest.CommandBased.essentials.SubsystemBase;
import com.everest.constants.Constants;
import com.everest.constants.Constants.ElevatorConstants;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import lombok.Getter;
import lombok.Setter;

public class SubsystemOuttake extends SubsystemBase {
    private final DcMotorEx rightEngine, leftEngine;
    private final Telemetry telemetry;
    private double memoryRight = 0;
    private double memoryLeft = 0;
    @Getter
    private double targetVelocity = 0;
    double targetVelocity_RPM;
    private double lastSeenRight = 0,

    lastSeenLeft = 0;

    boolean lastTimeLaunch = false;

    int timeLaunch = 0;


    @Getter
            @Setter
    double power;

    @Setter
    private String name;
    private ElapsedTime time = new ElapsedTime(ElapsedTime.Resolution.SECONDS);

    private final RevColorSensorV3 ColorSensorL, ColorSensorR, sensorGateLeft, sensorGateRight,sensorGateRight2;
    public SubsystemOuttake(HardwareMap hardwareMap, Telemetry telemetry){
        leftEngine = hardwareMap.get(DcMotorEx.class,"MOUTL");
        rightEngine = hardwareMap.get(DcMotorEx.class,"MOUTR");
        leftEngine.setDirection(DcMotorSimple.Direction.REVERSE);
        ColorSensorL = hardwareMap.get(RevColorSensorV3.class,"ColorSensorL");
        ColorSensorR = hardwareMap.get(RevColorSensorV3.class,"ColorSensorR");
        sensorGateLeft = hardwareMap.get(RevColorSensorV3.class,"SensorgateLeft");
        sensorGateRight = hardwareMap.get(RevColorSensorV3.class,"SensorgateRight");
        sensorGateRight2 = hardwareMap.get(RevColorSensorV3.class,"sensorgateright2");
        this.telemetry = telemetry;

        leftEngine.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        leftEngine.setVelocityPIDFCoefficients(9, 6, 0, 0);
        rightEngine.setVelocityPIDFCoefficients(9, 6, 0, 0);
        CommandScheduler.getInstance().registerSubsystem(this);
    }
    public void setVelocitys(double velocity){
        double vel = Limiter(velocity);
        targetVelocity_RPM = vel;
        vel *= ElevatorConstants.REVERSE_TICK_CONVERSION ;
        targetVelocity = vel;
        rightEngine.setVelocity(-vel);
        leftEngine.setVelocity(-vel);
    }
    public double Limiter(double input){
        if (Math.abs(input)> Constants.OuttakeConstants.MAX_VELOCITY )
            return Constants.OuttakeConstants.MAX_VELOCITY;
        else
            return input;
    }

    public double distanceSensorL(){
        return (ColorSensorL.getDistance(DistanceUnit.MM));
    }
    public double distanceSensorR(){
        return (ColorSensorR.getDistance(DistanceUnit.MM));
    }

    public boolean getArtefatoLeft(){
        if (sensorGateLeft.getDistance(DistanceUnit.MM)< Constants.OuttakeConstants.ACTIVE_MIN_CONT_LEFT_SENSOR) {
            memoryLeft++;
            lastSeenLeft = time.time();
        }
        double deltaT = time.time()-lastSeenLeft;
        if(deltaT>Constants.OuttakeConstants.admissibleSeconds) memoryLeft = 0;
        return memoryLeft > Constants.OuttakeConstants.MAX_MEMORE_CONT;
    }
    public boolean getAutoArtefatoLeft(){
        if (sensorGateLeft.getDistance(DistanceUnit.MM)< Constants.OuttakeConstants.ACTIVE_MIN_CONT_LEFT_SENSOR) {
            memoryLeft++;
            lastSeenLeft = time.time();
        }
        double deltaT = time.time()-lastSeenLeft;
        if(deltaT>Constants.OuttakeConstants.AutoAdmissibleSeconds) memoryLeft = 0;
        return memoryLeft > 1;
    }
    /*public boolean noDebounceLeft(){
        if (sensorGateLeft.getDistance(DistanceUnit.MM)< Constants.OuttakeConstants.ACTIVE_MIN_CONT_LEFT_SENSOR) {
            memoryLeft++;
        }
        return memoryLeft > Constants.OuttakeConstants.MAX_MEMORE_CONT;
    }*/

    public boolean getArtefatoRight(){
        if (sensorGateRight.getDistance(DistanceUnit.MM)< Constants.OuttakeConstants.ACTIVE_MIN_CONT_RIGHT_SENSOR ||
        sensorGateRight2.getDistance(DistanceUnit.MM) < Constants.OuttakeConstants.ACTIVE_MIN_CONT_RIGHT_SENSOR) {
            memoryRight++;
            lastSeenRight = time.time();
        }
        double deltaT = time.time()-lastSeenRight;
        if(deltaT>Constants.OuttakeConstants.admissibleSeconds) memoryRight = 0;
        return memoryRight > Constants.OuttakeConstants.MAX_MEMORE_CONT;
    }
    public boolean getAutoArtefatoRight(){
        if (sensorGateRight.getDistance(DistanceUnit.MM)< Constants.OuttakeConstants.ACTIVE_MIN_CONT_RIGHT_SENSOR ||
                sensorGateRight2.getDistance(DistanceUnit.MM) < Constants.OuttakeConstants.ACTIVE_MIN_CONT_RIGHT_SENSOR) {
            memoryRight++;
            lastSeenRight = time.time();
        }
        double deltaT = time.time()-lastSeenRight;
        if(deltaT>Constants.OuttakeConstants.AutoAdmissibleSeconds) memoryRight = 0;
        return memoryRight > 1;
    }

   /* public boolean noDebounceRight(){
        if (sensorGateRight.getDistance(DistanceUnit.MM)< Constants.OuttakeConstants.ACTIVE_MIN_CONT_RIGHT_SENSOR) {
            memoryRight++;
        }

        return memoryRight > Constants.OuttakeConstants.MAX_MEMORE_CONT;
    }*/

    public  int artifacts(){
        int left = getArtefatoLeft()?1:0;
        int right = getArtefatoRight()?1:0;
        int center = hasArtifact()?1:0;
        return left+right+center;
    }
    public  int AutoArtifacts(){
        int left = getAutoArtefatoLeft()?1:0;
        int right = getAutoArtefatoRight()?1:0;
        int center = hasArtifact()?1:0;
        return left+right+center;
    }

    public boolean artifactsCondition(){
        return artifacts()==3;
    }
    public boolean artifactsConditionfor2(){
        return artifacts()==2;
    }
    public boolean NoneArtifacts(){
        return artifacts()==1;
    }


    /*public int noDebounceArtifacts(){
        int left = noDebounceLeft()?1:0;
        int right = noDebounceRight()?1:0;
        int center = hasArtifact()?1:0;
        return left+right+center;
    }*/

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
    public void resetmemore(){
        memoryLeft = 0;
        memoryRight = 0;
    }


    public void brake(){
        rightEngine.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftEngine.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    private boolean rightSetpoint(double admissibleError){
        double velocity = Math.abs(rightEngine.getVelocity());
        if (velocity == 0 || targetVelocity == 0)
            return false;
        return Math.abs(velocity-targetVelocity)<admissibleError;
    }
    private boolean leftSetpoint(double admissibleError){
        double velocity =  Math.abs(leftEngine.getVelocity());
        if (velocity == 0 || targetVelocity == 0)
            return false;
        return Math.abs(velocity-targetVelocity)<admissibleError;
    }
    private boolean diferenceSetpoint(double admissibleError){
        double velocityL=  Math.abs(leftEngine.getVelocity());
        double velocityR = Math.abs(rightEngine.getVelocity());
        return  Math.abs(velocityR - velocityL)<admissibleError;
    }

    public boolean oneSent(){ return newTimeLaunch() >=1;}
    public boolean atSetpoint(double admissibleError){
        return rightSetpoint(admissibleError)&&leftSetpoint(admissibleError)&&diferenceSetpoint(admissibleError);
    }

    public boolean hasArtifact(){
        return distanceSensorR() < Constants.OuttakeConstants.ACTIVE_MIN_CONT_OUT_SENSOR ||
                distanceSensorL() < Constants.OuttakeConstants.ACTIVE_MIN_CONT_OUT_SENSOR;
    }


    @Override
    public void periodic() {
        telemetry.addData("outtake-targetVelocity-RPM", targetVelocity_RPM);

       /* telemetry.addData("Dist outR", distanceSensorR());
        telemetry.addData("Dist outL", distanceSensorL());

        telemetry.addData("Dist R2", sensorGateRight2.getDistance(DistanceUnit.MM));
        telemetry.addData("Dist R", sensorGateRight.getDistance(DistanceUnit.MM));

        telemetry.addData("Dist L ", sensorGateLeft.getDistance(DistanceUnit.MM));*/


        telemetry.addData("Artifacts:", artifacts());


    }

    public Command waitDelay(double waitSeconds){
        return new Command() {
            private ElapsedTime timer = new ElapsedTime(ElapsedTime.Resolution.SECONDS);

            @Override
            public void initialize() {
                timer.reset();
            }

            @Override
            public void execute() {
                if (sensorGateLeft.getDistance(DistanceUnit.MM) < Constants.OuttakeConstants.ACTIVE_MIN_CONT_LEFT_SENSOR ||
                        sensorGateRight.getDistance(DistanceUnit.MM) < Constants.OuttakeConstants.ACTIVE_MIN_CONT_RIGHT_SENSOR ||
                        hasArtifact() ||
                        sensorGateRight2.getDistance(DistanceUnit.MM) < Constants.OuttakeConstants.ACTIVE_MIN_CONT_RIGHT_SENSOR
                )
                    timer.reset();
            }

            @Override
            public boolean isFinished() {
                return timer.time() > waitSeconds;
            }
        };
    }

    @NonNull
    @Override
    public String toString() {
        return  name;
    }
}
