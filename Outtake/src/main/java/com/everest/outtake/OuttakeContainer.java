package com.everest.outtake;

import static com.everest.constants.Constants.ControllerConstants.GAMEPAD_AIM_TRIGGER;

import com.everest.CommandBased.essentials.Trigger;
import com.everest.constants.Constants;
import com.everest.constants.Pattern;
import com.everest.outtake.command.AutoLime3A;
import com.everest.outtake.command.LaunchCommand;
import com.everest.outtake.subsystem.SubsystemOuttake;
import com.qualcomm.robotcore.hardware.Gamepad;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

import lombok.Builder;

@Builder
public class OuttakeContainer implements com.everest.constants.meta.RobotContainer {
    private final Gamepad gamepad1;

    private final Supplier<Double> distance;

    private final SubsystemOuttake subsystem;

    private final BooleanSupplier sarcophagiMoment;

    private final BooleanSupplier hasArtifact;
    @Override
    public void mainRoutine() {
        subsystem.setDefaultCommand(
                new AutoLime3A(distance, subsystem).ateQUe(()->
                        gamepad1.left_trigger<=GAMEPAD_AIM_TRIGGER||
                                (Constants.getMatchPattern().equals(Pattern.BOTTOM)&&
                                        sarcophagiMoment.getAsBoolean()&&
                                        !hasArtifact.getAsBoolean())));

        new Trigger(hasArtifact).whileFalse(new LaunchCommand(subsystem, -0.2));
    }
}
