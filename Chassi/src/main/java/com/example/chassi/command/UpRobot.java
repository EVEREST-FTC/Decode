package com.example.chassi.command;

import com.everest.CommandBased.definition.Command;
import com.example.chassi.MecanumDrive;

public class UpRobot extends Command {


        final MecanumDrive chassi;

    public UpRobot(MecanumDrive chassi) {
        this.chassi = chassi;
        addRequirements(chassi);
    }


        @Override
        public void execute() {chassi.setPositionEleveitor(90);}

        @Override
        public void end(boolean interrupted) {
            chassi.setPositionEleveitor(0);
    }

}
