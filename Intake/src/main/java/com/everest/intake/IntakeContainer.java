package com.everest.intake;

import com.everest.CommandBased.compositions.SelectCommand;
import com.everest.CommandBased.essentials.Trigger;
import com.everest.constants.Constants;
import com.everest.intake.Command.CommandIntake;
import com.everest.intake.Subsystem.SubsytemIntake;
import com.qualcomm.robotcore.hardware.Gamepad;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

import lombok.Builder;

@Builder
public class IntakeContainer implements com.everest.constants.meta.RobotContainer {

    private final SubsytemIntake subsytemIntake;

    private final Gamepad gamepad;

    private final BooleanSupplier hasartifact;
    private final BooleanSupplier intakemoment;
    private final DoubleSupplier distance;

    @Override
    public void mainRoutine() {


        new Trigger(()->gamepad.y).toggleOnTrue(new CommandIntake(subsytemIntake,0));


        subsytemIntake.setDefaultCommand(new CommandIntake(subsytemIntake, Constants.INTAKE_POWER));

        new Trigger(()->gamepad.left_trigger>0.9).and(hasartifact).whileTrue(new CommandIntake(subsytemIntake, 0));

        /// longe
        new Trigger(intakemoment)
                .and(()->gamepad.left_trigger>0.9)
                .and(()->!hasartifact.getAsBoolean())
                .and(()->distance.getAsDouble()>1.79).whileTrue(
                new CommandIntake(subsytemIntake,.8));

        /// perto
        new Trigger(intakemoment)
                .and(()->gamepad.left_trigger>0.9)
                .and(()->!hasartifact.getAsBoolean())
                .whileTrue(
                        new CommandIntake(subsytemIntake,.85));
    }
}
