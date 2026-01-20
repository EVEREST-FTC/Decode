package com.everest.intake;

import static com.everest.constants.Constants.CameraConstants.largeIncrementDistance;
import static com.everest.constants.Constants.CameraConstants.shortIncrementDistance;
import static com.everest.constants.Constants.ControllerConstants.GAMEPAD_AIM_TRIGGER;
import static com.everest.constants.Constants.IntakeConstants.CLOSE_LAST_INTAKE_POWER;
import static com.everest.constants.Constants.IntakeConstants.INTAKE_POWER;
import static com.everest.constants.Constants.IntakeConstants.LAST_INTAKE_POWER;

import com.everest.CommandBased.essentials.Trigger;
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
    private final BooleanSupplier sarcophagiMoment;
    private final BooleanSupplier isUnactive;
    private final DoubleSupplier distance;
    @Override
    public void mainRoutine() {
        new Trigger(()->gamepad.y).toggleOnTrue(new CommandIntake(subsytemIntake,0));
        subsytemIntake.setDefaultCommand(new CommandIntake(subsytemIntake, INTAKE_POWER));
        new Trigger(sarcophagiMoment).and(()->!isUnactive.getAsBoolean()).whileTrue(new CommandIntake(subsytemIntake, 0.2));
        new Trigger(()->gamepad.left_trigger> GAMEPAD_AIM_TRIGGER).and(hasArtifact).whileTrue(new CommandIntake(subsytemIntake, 0));
        /// longe
        //TODO: tirar os numeros hardcoded
        new Trigger(intakeMoment)
                .and(()->gamepad.left_trigger>GAMEPAD_AIM_TRIGGER)
                .and(()->!hasArtifact.getAsBoolean())
                .and(()->
                        distance.getAsDouble()>shortIncrementDistance&&
                        distance.getAsDouble()<largeIncrementDistance).whileTrue(
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
