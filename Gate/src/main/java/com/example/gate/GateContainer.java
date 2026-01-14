package com.example.gate;

import com.everest.CommandBased.compositions.SelectCommand;
import com.everest.CommandBased.essentials.Trigger;
import com.everest.CommandBased.util.ConditionalCommand;
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
    private final BooleanSupplier sarcofagoMoment;
    private final BooleanSupplier sensorSarcofogo;




    @Override
    public void mainRoutine() {
        ///bloqueio pro sarcofag
        /*new Trigger(()->(sensorSarcofogo.getAsBoolean() || artifactMoment.getAsBoolean())).whileTrue(
                new Command(subsystemGate, Constants.GateClosePosition).ateQUe(()->!hasArtifact.getAsBoolean())
        );*/
        new Trigger(sarcofagoMoment).onTrue(
                new Command(subsystemGate, Constants.GateClosePosition).ateQUe(hasArtifact)
        );

    }

    @Override
    public void states() {
        /// bloqueio pro outtake
        subsystemGate.setDefaultCommand(
                new SelectCommand<>(
                        Map.ofEntries(
                                Map.entry(State.CLOSED, new Command(subsystemGate, Constants.GateClosePosition)),
                                Map.entry(State.OPENED, new Command(subsystemGate,Constants.GateOpenPosition))
                        ),
                        ()->State.selector(hasArtifact.getAsBoolean())
                ).antesDe(new ConditionalCommand(()->!sensorSarcofogo.getAsBoolean()))
        );
    }
}
