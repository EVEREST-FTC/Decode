package com.example.gate;

import com.everest.CommandBased.essentials.Trigger;
import com.everest.constants.Constants;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.function.BooleanSupplier;

public class RobotContainer {
    private final Gamepad gamepad;
    private final SubsystemGate subsystemGate;
    private final BooleanSupplier hasArtifact;

    public RobotContainer(HardwareMap hardwareMap,
                          Telemetry telemetry,
                          Gamepad gamepad,
                          SubsystemGate subsystemGate, BooleanSupplier hasArtifcat) {

        this.gamepad = gamepad;
        this.subsystemGate = subsystemGate;
        this.hasArtifact = hasArtifcat;
        triggerAssociations();
    }

    private void triggerAssociations(){
        subsystemGate.setDefaultCommand(
                new Command(subsystemGate,0)
        );
        new Trigger(hasArtifact).toggleOnTrue( new Command(subsystemGate,Constants.GateInitialPosition));
    }
}
