package com.everest.trigger.subsystem;

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
import java.util.function.Supplier;

import lombok.Getter;
import lombok.Setter;

public class TriggerSubsystem extends SubsystemBase {


    Servo ServoLG, ServoRG;
    Telemetry telemetry;
    double lastLeft, lastRight;
    @Getter
    int timelaunch = 0;
    @Getter
    @Setter
    int lastTarget = 3;
    public TriggerSubsystem(HardwareMap hardwareMap, Telemetry telemetry){
        ServoLG = hardwareMap.get(Servo.class,"ServoLG");
        ServoRG = hardwareMap.get(Servo.class,"ServoRG");
        resetPosiiton();
        timelaunch = 0;
        this.telemetry = telemetry;
        CommandScheduler.getInstance().registerSubsystem(this);
    }
    public boolean contlaunchtimes(){
        return timelaunch == lastTarget;
    }
    public boolean intaketimepower(){
        return timelaunch > 1;
    }
    public void resettimelaunch(){
        timelaunch = 0;
    }


    public void setPositionR(double alvo){
        lastRight = alvo;
        ServoRG.setPosition(alvo);
    }
    public void setPositionL(double alvo){
        lastLeft = alvo;
        ServoLG.setPosition(alvo);
    }


    @Override
    public void periodic() {
        /*
        telemetry.addData("ordinal",  Constants.matchPattern.ordinal());
        telemetry.addData("artifact moment ", artifactmoment());
        telemetry.addData("right target", lastRight);
        telemetry.addData("left target", lastLeft);*/
        telemetry.addData("artefatos lancados",timelaunch);
        telemetry.addData("artefatos vistos", lastTarget);
    }

    public boolean artifactmoment(){
        return Constants.matchPattern.ordinal() == timelaunch;
    }
    public void incrementTImeLaunch(){
        timelaunch += 1;
    }
    public void resetPosiiton(){

        setPositionL(Constants.leftInitialPosition);
        setPositionR(Constants.rightInitialPosition);
    }

    public Command launch(BooleanSupplier hasArtifact,
                          BooleanSupplier motorPower,
                          BooleanSupplier chassis){
        return new TriggerCommand(
                this,
                Constants.targetLeftPosition,
                Constants.targetRightPosition,
                ()->{}
        ).ateQUe(()->!hasArtifact.getAsBoolean()).
                antesDe(new ConditionalCommand(
                        ()->(
                                motorPower.getAsBoolean())
                                &&(hasArtifact.getAsBoolean()
                                &&(chassis.getAsBoolean())))
                );
    }
}