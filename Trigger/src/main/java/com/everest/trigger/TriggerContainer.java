package com.everest.trigger;

import static com.everest.constants.Constants.ControllerConstants.GAMEPAD_AIM_TRIGGER;

import com.everest.CommandBased.compositions.RepeatCommand;
import com.everest.CommandBased.definition.Command;
import com.everest.CommandBased.essentials.Trigger;
import com.everest.CommandBased.util.ConditionalCommand;
import com.everest.constants.meta.RobotContainer;
import com.everest.trigger.subsystem.TriggerSubsystem;
import com.qualcomm.robotcore.hardware.Gamepad;

import java.util.function.BooleanSupplier;

import lombok.Builder;

@Builder
public class TriggerContainer implements RobotContainer {
    private final TriggerSubsystem triggerSubsystem;
    private final Gamepad gamepad1;
    private final Gamepad gamepad2;
    private final BooleanSupplier velocityVerifier;
    private final BooleanSupplier limelightAcceptance;
    public final  BooleanSupplier hasArtifact;
    public final BooleanSupplier translationalSetpoint;
    private final Runnable resetMemory;
    @Override
    public void mainRoutine() {
        new Trigger(()->gamepad1.left_trigger>GAMEPAD_AIM_TRIGGER).whileTrue(
                        new RepeatCommand(
                                triggerSubsystem.launch(resetMemory)
                                        .ateQUe(()->!hasArtifact.getAsBoolean()).
                                        antesDe(conditionalCommand())
                        ).finalmente(triggerSubsystem::resetTimeLaunch));
        new Trigger(()->gamepad2.left_trigger>GAMEPAD_AIM_TRIGGER).whileTrue(
                new RepeatCommand(
                        triggerSubsystem.launch(resetMemory)
                                .ateQUe(()->!hasArtifact.getAsBoolean()).
                                antesDe(new ConditionalCommand(()->velocityVerifier.getAsBoolean()
                                        &&hasArtifact.getAsBoolean()))
                ).finalmente(triggerSubsystem::resetTimeLaunch));

    }
    private Command conditionalCommand(){
        BooleanSupplier triggerCondition = ()->
                limelightAcceptance.getAsBoolean()
                        &&velocityVerifier.getAsBoolean()
                        &&hasArtifact.getAsBoolean()
                        &&translationalSetpoint.getAsBoolean();
        return new ConditionalCommand(triggerCondition);
    }
}
