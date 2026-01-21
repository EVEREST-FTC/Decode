package com.example.chassi.command;

import com.everest.CommandBased.definition.Command;
import com.example.chassi.MecanumDrive;
public class UpRobot extends Command {
    private final MecanumDrive chassis;

    public UpRobot(MecanumDrive chassis) {
        this.chassis = chassis;
        addRequirements(chassis);
    }


        @Override
        public void execute() {
            chassis.setPositionElevator(90);}

        @Override
        public void end(boolean interrupted) {
            chassis.setPositionElevator(0);
    }

}
