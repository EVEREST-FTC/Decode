package com.everest.intake.Command;

import com.everest.CommandBased.definition.Command;
import com.everest.constants.Constants;
import com.everest.intake.Subsystem.SubsytemIntake;

public class CommandIntake extends Command {
    private final SubsytemIntake subsytemIntake;
    private final double power;
    public CommandIntake(SubsytemIntake subsytemIntake,double power) {
        this.subsytemIntake = subsytemIntake;
        this.power = power;
        addRequirements(subsytemIntake);
    }
    @Override
    public void execute() {
        subsytemIntake.startIntake(power);
    }
    @Override
    public void end(boolean interrupted) {
        subsytemIntake.startIntake(0);
        subsytemIntake.Braker();
    }
}
