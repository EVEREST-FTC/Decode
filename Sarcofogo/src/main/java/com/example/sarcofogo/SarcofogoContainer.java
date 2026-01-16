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
    private final Supplier<Integer> ArtifactComplete;
    private final BooleanSupplier artifactMoment;


    @Override
    public void mainRoutine() {
        /*new Trigger(()->gamepad.x).and(()->!hasArtifact.getAsBoolean()).whileTrue(new Command(subsystemSarcofogo,50));*/


        new Trigger(subsystemSarcofogo::getsensorSarcofogo).onTrue(
                new SelectCommand<>(
                Map.ofEntries(
                        Map.entry(Moment.KEEP, new Command(subsystemSarcofogo,
                                SarcofogoInitialPosition, Moment.KEEP)),
                        Map.entry(Moment.SEND, new Command(subsystemSarcofogo,50, Moment.SEND).ateQUe(()->!artifactMoment.getAsBoolean()))
                ),
                ()->Moment.select(artifactMoment.getAsBoolean())
        ));

        new Trigger(()->gamepad.left_trigger>GAMEPAD_AIM_TRIGGER).onFalse(new InstantCommand(subsystemSarcofogo::resetmemore));
        flagSubsystem.setDefaultCommand(
                new CommandBandeira(flagSubsystem, 0)
        );
        new Trigger(()->ArtifactComplete.get()==2).whileTrue(
                new CommandBandeira(flagSubsystem,45)
        );
        new Trigger(()->ArtifactComplete.get()==3).whileTrue(
                new CommandBandeira(flagSubsystem,90)
        );
    }
}
