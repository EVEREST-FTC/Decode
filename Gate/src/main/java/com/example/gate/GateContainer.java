package com.example.gate;

import static com.everest.constants.Constants.ControllerConstants.GAMEPAD_AIM_TRIGGER;
import static com.everest.constants.Constants.GateConstants.GateClosePosition;
import static com.everest.constants.Constants.GateConstants.GateOpenPosition;

import com.everest.CommandBased.compositions.SelectCommand;
import com.everest.CommandBased.essentials.Trigger;
import com.everest.constants.meta.RobotContainer;
import com.qualcomm.robotcore.hardware.Gamepad;

import java.util.Map;
import java.util.function.BooleanSupplier;

import lombok.Builder;

@Builder
public class GateContainer implements RobotContainer {
    private final SubsystemGate subsystemGate;
    private final BooleanSupplier hasArtifact;
    private final Gamepad gamepad;
    private final BooleanSupplier sarcophagiMoment;
    private final BooleanSupplier sensorSarcophagi;

    @Override
    public void mainRoutine() {
        ///bloqueio pro sarcofago
       new Trigger(sarcophagiMoment).whileTrue(
                new Command(subsystemGate, GateClosePosition)
        );

    }

    @Override
    public void states() {
        /// bloqueio pro outtake

        subsystemGate.setDefaultCommand(
                new SelectCommand<>(
                        Map.ofEntries(
                                Map.entry(State.CLOSED, new Command(subsystemGate, GateClosePosition)),
                                Map.entry(State.OPENED, new Command(subsystemGate,GateOpenPosition)),
                                Map.entry(State.BOTTOM_SELECTION, new Command(subsystemGate, GateClosePosition))
                        ),
                        ()->State.selector(hasArtifact.getAsBoolean(),
                                gamepad.left_trigger>GAMEPAD_AIM_TRIGGER)
                )
        );
    }
}
