package com.everest.outtake;

import com.everest.CommandBased.essentials.Trigger;
import com.everest.outtake.command.AutoLime3A;
import com.everest.outtake.subsystem.SubsystemOuttake;
import com.qualcomm.robotcore.hardware.Gamepad;

import java.util.function.Supplier;

import lombok.Builder;

@Builder
public class OuttakeContainer implements com.everest.constants.meta.RobotContainer {
    private final Gamepad gamepad1;

    private final Supplier<Double>distancia;

    private final SubsystemOuttake subsystem;

    @Override
    public void mainRoutine() {
        new Trigger(()->gamepad1.left_bumper).whileTrue(
                new AutoLime3A(distancia,subsystem)
        );
    }
}
