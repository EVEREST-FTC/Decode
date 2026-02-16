package com.everest.outtake.command;

import com.everest.CommandBased.definition.Command;
import com.everest.outtake.subsystem.SubsystemOuttake;

public class LaunchCommand  extends Command {


    private final SubsystemOuttake outtakeMotor;
    private final double velocity;


    public LaunchCommand(SubsystemOuttake outtakeMotor, double velocityPercentage) {
        this.outtakeMotor = outtakeMotor;
        this.velocity = velocityPercentage ;
        addRequirements(outtakeMotor);

    }

    @Override
    public void execute() {
        outtakeMotor.setVelocitys(velocity);
    }

}

