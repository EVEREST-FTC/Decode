package com.example.gate;

import com.everest.CommandBased.compositions.SelectCommand;
import com.everest.CommandBased.essentials.Trigger;
import com.everest.constants.Constants;
import com.everest.constants.meta.RobotContainer;
import com.everest.constants.meta.StateMachine;
import com.qualcomm.robotcore.hardware.Gamepad;

import java.util.Map;
import java.util.function.BooleanSupplier;

import lombok.Builder;

@Builder
public class GateContainer implements RobotContainer {
    private final SubsystemGate subsystemGate;
    private final BooleanSupplier hasArtifact;
    private final Gamepad gamepad;
    private final StateMachine stateMachine = new StateMachine(State.CLOSED);
    private final BooleanSupplier artifactMoment;




    @Override
    public void mainRoutine() {
        ///bloqueio pro carcofago
        new Trigger(()->artifactMoment.getAsBoolean()&&gamepad.x).whileTrue(
                new Command(subsystemGate, Constants.GateClosePosition)
        );







        /// bloqueio pro outtake
        subsystemGate.setDefaultCommand(
                new SelectCommand<>(
                        Map.ofEntries(
                                Map.entry(State.CLOSED, new Command(subsystemGate, Constants.GateClosePosition)),
                                Map.entry(State.OPENED, new Command(subsystemGate,Constants.GateOpenPosition))
                        ),
                        ()->State.selector(hasArtifact.getAsBoolean())
                )
        );
    }
}
