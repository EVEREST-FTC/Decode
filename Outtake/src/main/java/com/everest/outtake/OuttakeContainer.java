package com.everest.outtake;

import static com.everest.constants.Constants.ControllerConstants.GAMEPAD_AIM_TRIGGER;

import com.everest.CommandBased.compositions.SequentialCommandGroup;
import com.everest.CommandBased.essentials.Trigger;
import com.everest.CommandBased.util.WaitCommand;
import com.everest.constants.Constants;
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

    private final Supplier<Double>distancia;

    private final SubsystemOuttake subsystem;

    private final BooleanSupplier sarcofagoMoment;

    private final BooleanSupplier hasArtifact;



    @Override
    public void mainRoutine() {
        new Trigger(()->gamepad1.left_trigger>GAMEPAD_AIM_TRIGGER).whileTrue(
                new AutoLime3A(distancia,subsystem)
        );
    }
}
