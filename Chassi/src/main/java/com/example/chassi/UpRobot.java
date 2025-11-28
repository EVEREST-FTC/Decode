package com.example.chassi;

import com.everest.CommandBased.definition.Command;

import java.util.function.DoubleSupplier;

public class UpRobot extends Command {


        final Chassi chassi;

    public UpRobot(Chassi chassi) {
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
