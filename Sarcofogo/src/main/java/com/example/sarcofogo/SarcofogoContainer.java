package com.example.sarcofogo;

import com.everest.CommandBased.essentials.Trigger;
import com.everest.CommandBased.util.ConditionalCommand;
import com.everest.constants.Constants;
import com.qualcomm.robotcore.hardware.Gamepad;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

import lombok.Builder;

@Builder
public class SarcofogoContainer implements com.everest.constants.meta.RobotContainer {
    private final Gamepad gamepad;
    private final SubsystemSarcofogo subsystemSarcofogo;
    private final BooleanSupplier hasArtifact;
    private final BooleanSupplier ArtifactComplete;

    @Override
    public void mainRoutine() {
        new Trigger(()->gamepad.x).whileTrue(
                new Command(subsystemSarcofogo,34).antesDe(new ConditionalCommand(()->!hasArtifact.getAsBoolean()))
        );
        subsystemSarcofogo.setDefaultCommand(
                new CommandBandeira(subsystemSarcofogo, 0)
        );
        new Trigger(ArtifactComplete).whileTrue(
                new CommandBandeira(subsystemSarcofogo,90)
        );

    }
}
