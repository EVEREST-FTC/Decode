package com.example.gate;

import static com.everest.constants.Constants.ControllerConstants.GAMEPAD_AIM_TRIGGER;
import static com.everest.constants.Constants.GateConstants.GATE_OPEN_POWER;
import static com.everest.constants.Constants.GateConstants.GATE_CLOSE_POWER;

import com.everest.CommandBased.compositions.SelectCommand;
import com.everest.CommandBased.essentials.Trigger;
import com.everest.CommandBased.util.ConditionalCommand;
import com.everest.constants.Constants;
import com.everest.constants.meta.RobotContainer;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Const;

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
    private final BooleanSupplier isUnactive;
    private final BooleanSupplier timelaunch;


    private final BooleanSupplier velocityVerifier;
    private final BooleanSupplier limelightAcceptance;
    public final BooleanSupplier translationalSetpoint;

    @Override
    public void mainRoutine() {
        ///bloqueio pro sarcofago
       /*new Trigger(sarcophagiMoment).whileTrue(
                new Command(subsystemGate, GATE_CLOSE_POWER,GATE_OPEN_POWER)
        );*/
        /*new Trigger(hasArtifact).whileFalse(new Command(subsystemGate,GATE_OPEN_POWER,GATE_CLOSE_POWER));*/


    }

    @Override
    public void states() {
        /// bloqueio pro outtake
        subsystemGate.setDefaultCommand(
                new SelectCommand<>(
                        Map.ofEntries(
                                Map.entry(State.CLOSED, new Command(subsystemGate, GATE_OPEN_POWER,GATE_CLOSE_POWER)),
                                Map.entry(State.OPENED, new Command(subsystemGate, GATE_CLOSE_POWER,GATE_OPEN_POWER))
                        ),
                        ()->State.selector(
                                !hasArtifact.getAsBoolean()/*&&gamepad.left_trigger<=GAMEPAD_AIM_TRIGGER )||
                                (gamepad.left_trigger>GAMEPAD_AIM_TRIGGER
                                && limelightAcceptance.getAsBoolean()
                                &&velocityVerifier.getAsBoolean()
                                &&!hasArtifact.getAsBoolean())*/
                        )
                )
        );
    }

}
