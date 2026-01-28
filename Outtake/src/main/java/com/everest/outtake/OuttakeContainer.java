package com.everest.outtake;

import static com.everest.constants.Constants.ControllerConstants.GAMEPAD_AIM_TRIGGER;
import static com.everest.constants.Constants.PlatformConstants.CLOSE_POWER_LAUNCHER_CONVERSION;
import static com.everest.constants.Constants.PlatformConstants.FAR_POWER_LAUNCHER_CONVERSION;
import static com.everest.constants.Constants.PlatformConstants.POWER_LAUNCHER_CONVERSION;

import com.everest.CommandBased.compositions.SelectCommand;
import com.everest.CommandBased.essentials.Trigger;
import com.everest.CommandBased.util.InstantCommand;
import com.everest.constants.Constants;
import com.everest.constants.Pattern;
import com.everest.outtake.command.AutoLime3A;
import com.everest.outtake.command.LaunchCommand;
import com.everest.outtake.subsystem.SubsystemOuttake;
import com.qualcomm.robotcore.hardware.Gamepad;

import java.util.Map;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

import lombok.Builder;

@Builder
public class OuttakeContainer implements com.everest.constants.meta.RobotContainer {
    private final Gamepad gamepad1;
    private final Gamepad gamepad2;

    private final Supplier<Double> distance;

    private final SubsystemOuttake subsystem;

    private final BooleanSupplier sarcophagiMoment;

    private final BooleanSupplier hasArtifact;
    private final BooleanSupplier isUnactive;

    @Override
    public void mainRoutine() {
        subsystem.setDefaultCommand(
                new AutoLime3A(distance, subsystem, FAR_POWER_LAUNCHER_CONVERSION, CLOSE_POWER_LAUNCHER_CONVERSION, POWER_LAUNCHER_CONVERSION)
                .ateQUe(()->
                        gamepad1.left_trigger<=GAMEPAD_AIM_TRIGGER||
                                (Constants.getMatchPattern().equals(Pattern.BOTTOM)&&
                                        sarcophagiMoment.getAsBoolean()&&
                                        !hasArtifact.getAsBoolean())));
        new Trigger(()->gamepad2.a).toggleOnTrue(new LaunchCommand(subsystem, 0.5));
        new Trigger(()->gamepad2.b).toggleOnTrue(new LaunchCommand(subsystem, 0.8));
        new Trigger(()->!hasArtifact.getAsBoolean()).and(()->!isUnactive.getAsBoolean()).whileTrue(new LaunchCommand(subsystem, -0.2));

    }
}
