package com.everest.trigger.command;

import com.everest.CommandBased.definition.Command;
import com.everest.trigger.subsystem.TriggerSubsystem;

import java.sql.PreparedStatement;

public class TriggerCommand extends Command {

    private final TriggerSubsystem outtakeServo;
    private final double leftPositionTarget,
                            rightPositionTarget;


    public TriggerCommand(TriggerSubsystem outtak,
                          double leftPositionTarget,
                          double rightPositionTarget) {
        this.outtakeServo = outtak;
        this.leftPositionTarget = leftPositionTarget;
        this.rightPositionTarget = rightPositionTarget;
        addRequirements(outtak);

    }

    @Override
    public void execute() {
        //hardcoded
        outtakeServo.setPositionL(leftPositionTarget);
        outtakeServo.setPositionR(rightPositionTarget);;
    }

    @Override
    public void end(boolean interrupted) {
        outtakeServo.resetPosiiton();

    }

}