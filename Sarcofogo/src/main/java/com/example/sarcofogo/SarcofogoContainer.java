package com.example.sarcofogo;

import com.everest.CommandBased.compositions.SelectCommand;
import com.everest.CommandBased.essentials.Trigger;
import com.everest.CommandBased.util.ConditionalCommand;
import com.everest.constants.Constants;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.sun.tools.doclint.Checker;

import java.util.Map;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

import lombok.Builder;

@Builder
public class SarcofogoContainer implements com.everest.constants.meta.RobotContainer {
    private final Gamepad gamepad;
    private final SubsystemSarcofogo subsystemSarcofogo;
    private final FlagSubsystem flagSubsystem;
    private final BooleanSupplier hasArtifact;
    private final BooleanSupplier ArtifactComplete;
    private final BooleanSupplier artifactMoment;


    @Override
    public void mainRoutine() {
        new Trigger(()->gamepad.x).whileTrue(
                new SelectCommand<>(
                        Map.ofEntries(
                                Map.entry(Moment.KEEP, new Command(subsystemSarcofogo,
                                        Constants.SarcofogoInitialPosition)),
                                Map.entry(Moment.SEND, new Command(subsystemSarcofogo,34))
                        ),
                        ()->Moment.select(artifactMoment.getAsBoolean())
                )
        );

        subsystemSarcofogo.setDefaultCommand(new Command(subsystemSarcofogo,
                Constants.SarcofogoInitialPosition)
        );
        flagSubsystem.setDefaultCommand(
                new CommandBandeira(flagSubsystem, 0)
        );
        new Trigger(ArtifactComplete).whileTrue(
                new CommandBandeira(flagSubsystem,90)
        );

    }
}
