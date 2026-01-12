package com.everest.trigger;

import com.everest.CommandBased.compositions.ParallelCommandGroup;
import com.everest.CommandBased.compositions.RepeatCommand;
import com.everest.CommandBased.definition.Command;
import com.everest.CommandBased.essentials.Trigger;
import com.everest.CommandBased.util.ConditionalCommand;
import com.everest.constants.Constants;
import com.everest.constants.meta.RobotContainer;
import com.everest.trigger.command.TriggerCommand;
import com.everest.trigger.subsystem.TriggerSubsystem;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.function.BooleanSupplier;

import lombok.Builder;

@Builder
public class TriggerContainer implements RobotContainer {
    private final TriggerSubsystem triggerSubsystem;
    private final Gamepad gamepad;
    private final BooleanSupplier velocityVerifier;
    private final BooleanSupplier limelightAcceptance;
    public final  BooleanSupplier hasartifact;
    public final BooleanSupplier translationalSetpoint;
    private final Runnable resetMemore;
    @Override
    public void mainRoutine() {
        new Trigger(()->gamepad.left_trigger>0.9).whileTrue(
                        new RepeatCommand(
                                new TriggerCommand(
                                        triggerSubsystem,
                                        Constants.targetLeftPosition,
                                        Constants.targetRightPosition,
                                        resetMemore
                                ).ateQUe(()->!hasartifact.getAsBoolean()).
                                        antesDe(new ConditionalCommand(
                                                ()->(
                                                        limelightAcceptance.getAsBoolean()&&
                                                        velocityVerifier.getAsBoolean())
                                                        &&(hasartifact.getAsBoolean()
                                                        &&translationalSetpoint.getAsBoolean())
                                        ))).finalmente(
                                triggerSubsystem::resettimelaunch
                        )

        );
}}
