package com.everest.trigger;

import com.everest.CommandBased.essentials.Trigger;
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

    public RobotContainer(HardwareMap hardwareMap,
                          Telemetry telemetry,
                          Gamepad gamepad, BooleanSupplier velocityVerifier) {
        this.velocityVerifier = velocityVerifier;
        this.triggerSubsystem = new TriggerSubsystem(hardwareMap, telemetry);
        this.gamepad = gamepad;
        triggerAssociations();
    }

    private void triggerAssociations(){
        new Trigger(()->gamepad.left_bumper).and(velocityVerifier).toggleOnTrue(
                new TriggerCommand(
                        triggerSubsystem,
                        Constants.targetLeftPosition,
                        Constants.targetRightPosition
                ).espere(1, Constants.clockSeconds)
        );
    }
}
