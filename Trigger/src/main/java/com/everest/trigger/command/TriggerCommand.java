package com.everest.trigger.command;

import com.everest.CommandBased.definition.Command;
import com.everest.trigger.subsystem.TriggerSubsystem;

public class TriggerCommand extends Command {

    private final TriggerSubsystem outtakeServo;
    private final double leftPositionTarget,
                            rightPositionTarget;
    private final Runnable resetSarcophagi;


    public TriggerCommand(TriggerSubsystem outtake,
                          double leftPositionTarget,
                          double rightPositionTarget, Runnable resetSarcophagi) {
        this.outtakeServo = outtake;
        this.leftPositionTarget = leftPositionTarget;
        this.rightPositionTarget = rightPositionTarget;
        this.resetSarcophagi = resetSarcophagi;
        addRequirements(outtake);

    }

    @Override
    public void execute() {
        outtakeServo.setPositionL(leftPositionTarget);
        outtakeServo.setPositionR(rightPositionTarget);
    }

    @Override
    public void end(boolean interrupted) {
        outtakeServo.resetPosition();
        if(interrupted) return;
        outtakeServo.incrementTImeLaunch();
        resetSarcophagi.run();

    }

}