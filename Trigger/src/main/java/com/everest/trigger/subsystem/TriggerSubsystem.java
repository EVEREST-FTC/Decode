package com.everest.trigger.subsystem;

import static com.everest.constants.Constants.TriggerConstants.leftInitialPosition;
import static com.everest.constants.Constants.TriggerConstants.rightInitialPosition;
import static com.everest.constants.Constants.TriggerConstants.targetLeftPosition;
import static com.everest.constants.Constants.TriggerConstants.targetRightPosition;
import static com.everest.constants.Constants.clockSeconds;

import com.everest.CommandBased.definition.Command;
import com.everest.CommandBased.definition.CommandScheduler;
import com.everest.CommandBased.essentials.SubsystemBase;
import com.everest.CommandBased.util.ConditionalCommand;
import com.everest.constants.Constants;
import com.everest.trigger.command.TriggerCommand;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.function.BooleanSupplier;

import lombok.Getter;
import lombok.Setter;

public class TriggerSubsystem extends SubsystemBase {


    Servo ServoLG, ServoRG;
    Telemetry telemetry;
    double lastLeft, lastRight;

    @Getter
    int timeLaunch = 0;
    @Getter
    @Setter
    int lastTarget = 3;
    public TriggerSubsystem(HardwareMap hardwareMap, Telemetry telemetry){
        ServoLG = hardwareMap.get(Servo.class,"ServoLG");
        ServoRG = hardwareMap.get(Servo.class,"ServoRG");
        resetPosition();
        timeLaunch = 0;
        lastTarget = 3;
        this.telemetry = telemetry;
        CommandScheduler.getInstance().registerSubsystem(this);
    }
    public boolean contLaunchTimes(){
        return timeLaunch == lastTarget;

    }
    public boolean intakeTimePower(){
        return timeLaunch > 1;
    }
    public void resetTimeLaunch(){
        timeLaunch = 0;
    }
    public boolean gatetime(){
        return timeLaunch == 0;
    }


    public void setPositionR(double target){
        lastRight = target;
        ServoRG.setPosition(target);
    }
    public void setPositionL(double Target){
        lastLeft = Target;
        ServoLG.setPosition(Target);
    }

    public boolean artifactMoment(){
        return Constants.getMatchPattern().ordinal() == timeLaunch;
    }
    public void incrementTImeLaunch(){
        timeLaunch++;
    }
    public void resetPosition(){

        setPositionL(leftInitialPosition);
        setPositionR(rightInitialPosition);
    }

    public Command launch(Runnable sarcophagi){
        return new TriggerCommand(
                this,
                targetLeftPosition,
                targetRightPosition,
                sarcophagi
        ).espere(0.1, clockSeconds);
    }

    public Command launch(BooleanSupplier hasArtifact,
                          BooleanSupplier motorPower){
        return new TriggerCommand(

                this,
                targetLeftPosition,

                targetRightPosition,
                ()->{}
        ).ateQUe(()->!hasArtifact.getAsBoolean()).
                antesDe(new ConditionalCommand(
                        ()->(
                                motorPower.getAsBoolean())
                                &&(hasArtifact.getAsBoolean()))
                );
    }

    @Override
    public void periodic() {
    /*telemetry.addData("timelaunch",timeLaunch);*/
    }
}