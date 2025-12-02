package com.everest.trigger;

import com.everest.CommandBased.compositions.RepeatCommand;
import com.everest.CommandBased.essentials.Trigger;
import com.everest.CommandBased.util.ConditionalCommand;
import com.everest.constants.Constants;
import com.everest.trigger.command.TriggerCommand;
import com.everest.trigger.subsystem.TriggerSubsystem;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

public class RobotContainer {
    private final TriggerSubsystem triggerSubsystem;
    private final Gamepad gamepad;
    private final BooleanSupplier velocityVerifier;

    public final  BooleanSupplier hasartifact;


    public RobotContainer(HardwareMap hardwareMap,
                          Telemetry telemetry,
                          Gamepad gamepad, BooleanSupplier velocityVerifier, BooleanSupplier hasartifact) {
        this.velocityVerifier = velocityVerifier;
        this.hasartifact = hasartifact;
        this.triggerSubsystem = new TriggerSubsystem(hardwareMap, telemetry);
        this.gamepad = gamepad;
        triggerAssociations();
    }

    private void triggerAssociations(){
        new Trigger(()->gamepad.left_bumper).toggleOnTrue(
                new RepeatCommand(
                new TriggerCommand(
                        triggerSubsystem,
                        Constants.targetLeftPosition,
                        Constants.targetRightPosition
                ).ateQUe(()->!hasartifact.getAsBoolean()).
                        antesDe(new ConditionalCommand(
                                ()->(velocityVerifier.getAsBoolean())&&(hasartifact.getAsBoolean()))
                        ))
        );
    }
}
