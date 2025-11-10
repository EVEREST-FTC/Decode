package com.everest.trigger;

import com.everest.CommandBased.essentials.Trigger;
import com.everest.trigger.command.TriggerCommand;
import com.everest.trigger.subsystem.TriggerSubsystem;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class RobotContainer {
    private final TriggerSubsystem triggerSubsystem;
    private final Gamepad gamepad;

    public RobotContainer(HardwareMap hardwareMap,
                          Telemetry telemetry,
                          Gamepad gamepad) {
        this.triggerSubsystem = new TriggerSubsystem(hardwareMap, telemetry);
        this.gamepad = gamepad;
    }

    private void triggerAssociations(){
        new Trigger(()->gamepad.left_bumper).toggleOnTrue(
                new TriggerCommand(
                        triggerSubsystem,
                        Constants.targetLeftPosition,
                        Constants.targetRightPosition
                ).espere(1, Constants.clockSeconds)
        );
    }
}
