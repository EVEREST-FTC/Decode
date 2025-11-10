package com.everest.outtake.command;

import com.everest.CommandBased.definition.Command;
import com.everest.outtake.Constants;
import com.everest.outtake.subsystem.Subsystem;

public class LaunchCommand  extends Command {


    private final Subsystem outtakeMotor;
    private final double velocity;


    public LaunchCommand(Subsystem outtakeMotor, double velocityPercentage) {
        if(Math.abs(velocityPercentage)>1) throw new RuntimeException("Porcentagem maior que 1. " +
                "Não faça isso,");
        this.outtakeMotor = outtakeMotor;
        this.velocity = velocityPercentage * Constants.maxRPM;
        addRequirements(outtakeMotor);

    }



    @Override
    public void execute() {
        outtakeMotor.setVelocity(velocity);
    }

    @Override
    public void end(boolean interrupted) {
        outtakeMotor.setVelocity(0);


    }
}

