package com.example.gate;

import static com.everest.constants.Constants.ControllerConstants.GAMEPAD_AIM_TRIGGER;
import static com.everest.constants.Constants.GateConstants.GATE_OPEN_POWER;
import static com.everest.constants.Constants.GateConstants.GATE_CLOSE_POWER;
import static com.everest.constants.Constants.GateConstants.GATE_SARCOFOGO_POWER;
import static com.everest.constants.Constants.robotTimer;

import com.everest.CommandBased.compositions.ParallelRaceGroup;
import com.everest.CommandBased.compositions.SelectCommand;
import com.everest.CommandBased.compositions.SequentialCommandGroup;
import com.everest.CommandBased.essentials.Trigger;
import com.everest.CommandBased.util.ConditionalCommand;
import com.everest.CommandBased.util.WaitCommand;
import com.everest.constants.Constants;
import com.everest.constants.meta.RobotContainer;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Const;

import java.util.Map;
import java.util.function.BooleanSupplier;

import lombok.Builder;

@Builder
public class GateContainer implements RobotContainer {
    /// subsitema da gate
    private final SubsystemGate subsystemGate;
    /// entradas de informações vindo de outros sistemas
    private final BooleanSupplier hasArtifact;
    private final Gamepad gamepad;
    private final BooleanSupplier sarcophagiMoment;
    private final BooleanSupplier isUnactive;


    @Override
    public void mainRoutine() {
        ///bloqueio pro sarcofago
       new Trigger(()->sarcophagiMoment.getAsBoolean()&&!hasArtifact.getAsBoolean()).whileTrue(
                new Command(subsystemGate, GATE_SARCOFOGO_POWER,GATE_OPEN_POWER)
        );
        new Trigger(()->gamepad.a).or(()->hasArtifact.getAsBoolean()&&!gamepad.left_trigger_pressed).onTrue(
                new Command(subsystemGate, -0.9,GATE_OPEN_POWER).espere(1, robotTimer)
        );
    }

    @Override
    public void states() {
        /// maquina de estados pro outtake
        subsystemGate.setDefaultCommand(
                new SelectCommand<>(
                        Map.ofEntries(
                                Map.entry(State.CLOSED, new Command(subsystemGate, GATE_CLOSE_POWER, GATE_OPEN_POWER)),
                                Map.entry(State.OPENED, new Command(subsystemGate, GATE_OPEN_POWER, GATE_CLOSE_POWER)),
                                Map.entry(State.BOTTOM_SELECTION, new Command(subsystemGate,GATE_CLOSE_POWER,GATE_CLOSE_POWER))
                        )
                        ,
                        ()->State.selector(
                                hasArtifact.getAsBoolean(),
                                gamepad.left_trigger>GAMEPAD_AIM_TRIGGER,
                                isUnactive.getAsBoolean())
                )
        );
    }

}
