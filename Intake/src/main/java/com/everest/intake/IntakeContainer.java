package com.everest.intake;

import static com.everest.constants.Constants.CameraConstants.shortIncrementDistance;
import static com.everest.constants.Constants.ControllerConstants.GAMEPAD_AIM_TRIGGER;
import static com.everest.constants.Constants.IntakeConstants.INTAKE_POWER;

import com.everest.CommandBased.compositions.SelectCommand;
import com.everest.CommandBased.essentials.Trigger;
import com.everest.constants.Constants;
import com.everest.intake.Command.CommandIntake;
import com.everest.intake.Subsystem.SubsytemIntake;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Const;

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


        subsytemIntake.setDefaultCommand(new CommandIntake(subsytemIntake, INTAKE_POWER));

        new Trigger(()->gamepad.left_trigger> GAMEPAD_AIM_TRIGGER).and(hasartifact).whileTrue(new CommandIntake(subsytemIntake, 0));

        /// longe
        new Trigger(intakemoment)
                .and(()->gamepad.left_trigger>GAMEPAD_AIM_TRIGGER)
                .and(()->!hasartifact.getAsBoolean())
                .and(()->distance.getAsDouble()>shortIncrementDistance).whileTrue(
                new CommandIntake(subsytemIntake,.8));

        /// perto
        new Trigger(intakemoment)
                .and(()->gamepad.left_trigger>GAMEPAD_AIM_TRIGGER)
                .and(()->!hasartifact.getAsBoolean())
                .whileTrue(
                        new CommandIntake(subsytemIntake,.85));
    }
}
