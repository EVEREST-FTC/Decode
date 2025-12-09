package com.everest.intake;

import com.everest.CommandBased.essentials.Trigger;
import com.everest.constants.Constants;
import com.everest.intake.Command.CommandIntake;
import com.everest.intake.Subsystem.SubsytemIntake;
import com.qualcomm.robotcore.hardware.Gamepad;

import lombok.Builder;

@Builder
public class IntakeContainer implements com.everest.constants.meta.RobotContainer {

    private final SubsytemIntake subsytemIntake;

    private final Gamepad gamepad;

    @Override
    public void mainRoutine() {
        new Trigger(()->gamepad.y).toggleOnTrue(new CommandIntake(subsytemIntake,0));
        subsytemIntake.setDefaultCommand(new CommandIntake(subsytemIntake, Constants.INTAKE_POWER));

    }
}
