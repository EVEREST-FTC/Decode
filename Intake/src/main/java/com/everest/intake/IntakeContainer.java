package com.everest.intake;

import com.everest.constants.Constants;
import com.everest.intake.Command.CommandIntake;
import com.everest.intake.Subsystem.SubsytemIntake;

import lombok.Builder;

@Builder
public class IntakeContainer implements com.everest.constants.meta.RobotContainer {

    private final SubsytemIntake subsytemIntake;

    @Override
    public void mainRoutine() {
        subsytemIntake.setDefaultCommand(new CommandIntake(subsytemIntake, Constants.INTAKE_POWER));

    }
}
