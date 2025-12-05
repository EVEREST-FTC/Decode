package com.example.gate;

import com.everest.CommandBased.compositions.SelectCommand;
import com.everest.CommandBased.compositions.SequentialCommandGroup;
import com.everest.CommandBased.essentials.Trigger;
import com.everest.CommandBased.util.WaitCommand;
import com.everest.constants.Constants;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.Map;
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

        /*subsystemGate.setDefaultCommand(
                new Command(subsystemGate,Constants.GateInitialPosition)
        );*/

        subsystemGate.setDefaultCommand(
                new SelectCommand<>(
                        Map.ofEntries(
                                Map.entry(State.CLOSED, new Command(subsystemGate,Constants.GateInitialPosition)),
                                Map.entry(State.OPENED, new Command(subsystemGate,0))
                        ),
                        ()->State.selector(hasArtifact.getAsBoolean())
                )
        );

        /*new Trigger(hasArtifact)
                .whileTrue( new Command(subsystemGate,Constants.GateInitialPosition).espere(0.3,Constants.clockSeconds))
                .whileFalse(new Command(subsystemGate,0));*/

    }
}
