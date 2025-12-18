package com.everest.outtake;

import com.everest.CommandBased.essentials.Trigger;
import com.everest.outtake.command.AutoLime3A;
import com.everest.outtake.command.LaunchCommand;
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
        new Trigger(()->gamepad1.left_trigger>0.9).whileTrue(
                new AutoLime3A(distancia,subsystem)
        );
    }
    public void testPower() {
        new Trigger(()->gamepad1.a).toggleOnTrue(
                new LaunchCommand(subsystem,6000*0.5)
        );
        new Trigger(()->gamepad1.b).toggleOnTrue(
                new LaunchCommand(subsystem,6000*0.6)
        );
        new Trigger(()->gamepad1.y).toggleOnTrue(
                new LaunchCommand(subsystem,6000*0.7)
        );
        new Trigger(()->gamepad1.x).toggleOnTrue(
                new LaunchCommand(subsystem,6000*0.8)
        );
        new Trigger(()->gamepad1.left_trigger>0.9).whileTrue(
                new AutoLime3A(distancia,subsystem)
        );

    }
}
