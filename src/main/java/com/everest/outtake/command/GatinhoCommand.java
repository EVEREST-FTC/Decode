package com.everest.outtake.command;

import com.everest.CommandBased.definition.Command;

import com.everest.outtake.subsystem.Subsystem;
import com.everest.outtake.subsystem.SubsystemGatilho;


public class GatinhoCommand extends Command {

    private SubsystemGatilho outtakServo;


    public GatinhoCommand(SubsystemGatilho outtakServo ) {
        addRequirements(outtakServo);

    }

    @Override
    public void execute() {
        outtakServo.setPositionL(0.89);
        outtakServo.setPositionR(0.15);;
    }

    @Override
    public void end(boolean interrupted) {
        outtakServo.setPositionL(0.97);
        outtakServo.setPositionR(0.05);

    }

}
