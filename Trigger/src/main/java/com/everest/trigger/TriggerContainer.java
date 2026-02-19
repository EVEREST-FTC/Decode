package com.everest.trigger;

import static com.everest.constants.Constants.ControllerConstants.GAMEPAD_AIM_TRIGGER;

import com.everest.CommandBased.compositions.RepeatCommand;
import com.everest.CommandBased.definition.Command;
import com.everest.CommandBased.essentials.Trigger;
import com.everest.CommandBased.util.ConditionalCommand;
import com.everest.CommandBased.util.InstantCommand;
import com.everest.constants.meta.RobotContainer;
import com.everest.trigger.subsystem.TriggerSubsystem;
import com.qualcomm.robotcore.hardware.Gamepad;

import java.util.function.BooleanSupplier;

import lombok.Builder;

@Builder
public class TriggerContainer implements RobotContainer {
    /// subsitema do gatilho
    private final TriggerSubsystem triggerSubsystem;
    /// entradas de informações vindo de outros sistemas
    private final Gamepad gamepad1;
    private final Gamepad gamepad2;
    private final BooleanSupplier velocityVerifier;
    private final BooleanSupplier limelightAcceptance;
    public final  BooleanSupplier hasArtifact;
    public final BooleanSupplier translationalSetpoint;
    private final Runnable resetMemory;
    private final Runnable resetOuttake;
    @Override
    public void mainRoutine() {
        ///  commando e logica para acinamento do gatilho em sequencia de acordo
        new Trigger(()->gamepad1.left_trigger>GAMEPAD_AIM_TRIGGER).whileTrue(
                        new RepeatCommand(
                                triggerSubsystem.launch(resetMemory)
                                        .ateQUe(()->!hasArtifact.getAsBoolean()).
                                        antesDe(conditionalCommand())
                        ).antesDe(new InstantCommand(()->{
                            triggerSubsystem.resetTimeLaunch();
                            resetOuttake.run();
                        })));
        new Trigger(()->gamepad2.left_trigger>GAMEPAD_AIM_TRIGGER).whileTrue(
                new RepeatCommand(
                        triggerSubsystem.launch(resetMemory)
                                .ateQUe(()->!hasArtifact.getAsBoolean()).
                                antesDe(new ConditionalCommand(()->velocityVerifier.getAsBoolean()
                                        &&hasArtifact.getAsBoolean()))
                ).finalmente(()->{
                    triggerSubsystem.resetTimeLaunch();
                    resetOuttake.run();
                }));

    }

    ///  conjunto de condições e verificações necessaria para o lançamento
    private Command conditionalCommand(){
        BooleanSupplier triggerCondition = ()->
                limelightAcceptance.getAsBoolean()
                        &&velocityVerifier.getAsBoolean()
                        &&hasArtifact.getAsBoolean()
                        &&translationalSetpoint.getAsBoolean();
        return new ConditionalCommand(triggerCondition);
    }
}
