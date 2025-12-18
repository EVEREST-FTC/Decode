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

public class TriggerSubsystem extends SubsystemBase {


    Servo ServoLG, ServoRG;
    Telemetry telemetry;

    int timelaunch = 0;
    public TriggerSubsystem(HardwareMap hardwareMap, Telemetry telemetry){
        ServoLG = hardwareMap.get(Servo.class,"ServoLG");
        ServoRG = hardwareMap.get(Servo.class,"ServoRG");
        resetPosiiton();

        this.telemetry = telemetry;
        CommandScheduler.getInstance().registerSubsystem(this);
    }
    public boolean contlaunchtimes(){
        return timelaunch == 3;
    }
    public void resettimelaunch(){
        timelaunch = 0;
    }


    public void setPositionR(double alvo){
        ServoRG.setPosition(alvo);
    }
    public void setPositionL(double alvo){
        ServoLG.setPosition(alvo);
    }


    @Override
    public void periodic() {
        telemetry.addData("trigger-time launch",contlaunchtimes());
        telemetry.addData("trigger-times launch",timelaunch);
    }

    public void resetPosiiton(){
        timelaunch += 1;
        setPositionL(Constants.leftInitialPosition);
        setPositionR(Constants.rightInitialPosition);
    }

    public Command launch(BooleanSupplier hasArtifact,
                          BooleanSupplier motorPower,
                          BooleanSupplier chassis){
        return new TriggerCommand(
                this,
                Constants.targetLeftPosition,
                Constants.targetRightPosition
        ).ateQUe(()->!hasArtifact.getAsBoolean()).
                antesDe(new ConditionalCommand(
                        ()->(
                                motorPower.getAsBoolean())
                                &&(hasArtifact.getAsBoolean()
                                &&(chassis.getAsBoolean())))
                );
    }
}