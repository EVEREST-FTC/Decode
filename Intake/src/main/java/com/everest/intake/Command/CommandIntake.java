package com.everest.intake.Command;

import com.everest.CommandBased.definition.Command;
import com.everest.constants.Constants;
import com.everest.intake.Subsystem.SubsytemIntake;

import java.util.function.DoubleSupplier;

public class CommandIntake extends Command {
    private final SubsytemIntake subsytemIntake;
    private final DoubleSupplier volatilePower;
    public CommandIntake(SubsytemIntake subsytemIntake, DoubleSupplier volatilePower) {
        this.subsytemIntake = subsytemIntake;
        this.volatilePower = volatilePower;
        addRequirements(subsytemIntake);
    }
    @Override
    public void execute() {
        subsytemIntake.startIntake(volatilePower.getAsDouble());
    }
    @Override
    public void end(boolean interrupted) {
        subsytemIntake.Braker();
    }
/*
    @Override
    public boolean isFinished() {
        return !subsytemIntake.isActive();
    }*/
}
