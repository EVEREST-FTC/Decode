package com.everest.trigger.command;

import com.everest.CommandBased.definition.Command;
import com.everest.constants.Constants;
import com.everest.constants.Pattern;
import com.everest.trigger.subsystem.TriggerSubsystem;

public class TriggerCommand extends Command {

    private final TriggerSubsystem outtakeServo;
    private final double leftPositionTarget,
                            rightPositionTarget;
    private final Runnable resetSarcofago;


    public TriggerCommand(TriggerSubsystem outtake,
                          double leftPositionTarget,
                          double rightPositionTarget, Runnable resetSarcofago) {
        this.outtakeServo = outtake;
        this.leftPositionTarget = leftPositionTarget;
        this.rightPositionTarget = rightPositionTarget;
        this.resetSarcofago = resetSarcofago;
        addRequirements(outtake);

    }

    @Override
    public void execute() {
        outtakeServo.setPositionL(leftPositionTarget);
        outtakeServo.setPositionR(rightPositionTarget);;
    }

    @Override
    public void end(boolean interrupted) {
        outtakeServo.incrementTImeLaunch();
        outtakeServo.resetPosiiton();
        resetSarcofago.run();

    }

}