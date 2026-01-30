package com.everest.intake;

import static com.everest.constants.Constants.CameraConstants.largeIncrementDistance;
import static com.everest.constants.Constants.CameraConstants.shortIncrementDistance;
import static com.everest.constants.Constants.ControllerConstants.GAMEPAD_AIM_TRIGGER;
import static com.everest.constants.Constants.IntakeConstants.CLOSE_LAST_INTAKE_POWER;
import static com.everest.constants.Constants.IntakeConstants.INTAKE_POWER;
import static com.everest.constants.Constants.IntakeConstants.INTAKE_POWER_NORMAL;
import static com.everest.constants.Constants.IntakeConstants.LAST_INTAKE_POWER;

import com.everest.CommandBased.compositions.ParallelCommandGroup;
import com.everest.CommandBased.compositions.ParallelRaceGroup;
import com.everest.CommandBased.compositions.RepeatCommand;
import com.everest.CommandBased.compositions.SequentialCommandGroup;
import com.everest.CommandBased.essentials.Trigger;
import com.everest.CommandBased.util.ConditionalCommand;
import com.everest.CommandBased.util.WaitCommand;
import com.everest.constants.Constants;
import com.everest.intake.Command.CommandIntake;
import com.everest.intake.Subsystem.SubsytemIntake;
import com.qualcomm.robotcore.hardware.Gamepad;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import lombok.Builder;

@Builder
public class IntakeContainer implements com.everest.constants.meta.RobotContainer {
    private final SubsytemIntake subsytemIntake;
    private final Gamepad gamepad;
    private final BooleanSupplier hasArtifact;
    private final BooleanSupplier intakeMoment;
    private final BooleanSupplier oneSent;
    private final BooleanSupplier velocityVerifier;

    private final BooleanSupplier sarcophagiMoment;
    private final BooleanSupplier isUnactive;
    private final DoubleSupplier distance;
    private final DoubleSupplier ArtifactComplete;
    @Override
    public void mainRoutine() {
       // new Trigger(()->!(gamepad.left_trigger> GAMEPAD_AIM_TRIGGER)).whileTrue(new CommandIntake(subsytemIntake, INTAKE_POWER_NORMAL));
        /// normal do lançamento
        subsytemIntake.setDefaultCommand(
                new CommandIntake(subsytemIntake, INTAKE_POWER_NORMAL).antesDe(new ConditionalCommand(()->!(gamepad.left_trigger> GAMEPAD_AIM_TRIGGER))));

        new Trigger(()->gamepad.y).toggleOnTrue(
                new CommandIntake(subsytemIntake, 0)
        );

        /// velocidade mais baixa pro sarcofago
        new Trigger(sarcophagiMoment).and(()->!isUnactive.getAsBoolean()).whileTrue(new CommandIntake(subsytemIntake, 0.2));

        /// parada pra lançamento
        new Trigger(()->gamepad.left_trigger> GAMEPAD_AIM_TRIGGER).and(hasArtifact).and(velocityVerifier).or(()->ArtifactComplete.getAsDouble()==3).or(()->gamepad.y)
                .whileTrue(new CommandIntake(subsytemIntake, 0));

        /// ta lancando e nao tem artefato no outtake
        new Trigger(()->gamepad.left_trigger> GAMEPAD_AIM_TRIGGER).and(()->!hasArtifact.getAsBoolean()).whileTrue(
                new CommandIntake(subsytemIntake, INTAKE_POWER)
        );

        /// acelerar manual
        new Trigger(()->gamepad.left_trigger> GAMEPAD_AIM_TRIGGER).and(()->gamepad.right_stick_button).whileTrue(new CommandIntake(subsytemIntake, LAST_INTAKE_POWER));

        /// potencia lançamento
        new Trigger(intakeMoment)
                .and(()->gamepad.left_trigger>GAMEPAD_AIM_TRIGGER)
                .and(()->!hasArtifact.getAsBoolean())
                .and(()->distance.getAsDouble()>shortIncrementDistance)
                .whileTrue(
                new CommandIntake(subsytemIntake, LAST_INTAKE_POWER));
        /// perto
        new Trigger(intakeMoment)
                .and(()->gamepad.left_trigger>GAMEPAD_AIM_TRIGGER)
                .and(()->!hasArtifact.getAsBoolean())
                .and(()->distance.getAsDouble()<largeIncrementDistance)
                .whileTrue(
                        new CommandIntake(subsytemIntake,CLOSE_LAST_INTAKE_POWER));
    }
}
