package com.example.sarcofogo;

import static com.everest.constants.Constants.ControllerConstants.GAMEPAD_AIM_TRIGGER;
import static com.everest.constants.Constants.SarcofagoConstants.SarcofogoInitialPosition;

import com.everest.CommandBased.compositions.SelectCommand;
import com.everest.CommandBased.essentials.Trigger;
import com.everest.CommandBased.util.ConditionalCommand;
import com.everest.CommandBased.util.InstantCommand;
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
    private final BooleanSupplier sensorSarcofogo;


    @Override
    public void mainRoutine() {
        new Trigger(sensorSarcofogo).whileTrue(
                new SelectCommand<>(
                Map.ofEntries(
                        Map.entry(Moment.KEEP, new Command(subsystemSarcofogo,
                                SarcofogoInitialPosition, Moment.KEEP)),
                        Map.entry(Moment.SEND, new Command(subsystemSarcofogo,50, Moment.SEND).espere(
                                2, Constants.clockSeconds
                        ))
                ),
                ()->Moment.select(artifactMoment.getAsBoolean())
        ));

        new Trigger(()->gamepad.left_trigger>GAMEPAD_AIM_TRIGGER).onFalse(new InstantCommand(subsystemSarcofogo::resetmemore));
        flagSubsystem.setDefaultCommand(
                new CommandBandeira(flagSubsystem, 0)
        );
        new Trigger(ArtifactComplete).whileTrue(
                new CommandBandeira(flagSubsystem,90)
        );
    }
}
