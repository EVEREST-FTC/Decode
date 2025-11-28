package com.everest.outtake.command;

import com.everest.CommandBased.definition.Command;
import com.everest.constants.Constants;
import com.everest.outtake.subsystem.SubsystemOuttake;

import java.util.function.Supplier;

public class AutoLime3A extends Command {
    final Supplier<Double>distanceSupplier;
    final SubsystemOuttake subsystem;




    public AutoLime3A(Supplier<Double> distanceSupplier, SubsystemOuttake subsystem) {
        this.distanceSupplier = distanceSupplier;
        this.subsystem = subsystem;
        addRequirements(subsystem);
    }

    @Override
    public void execute() {

        double distance = distanceSupplier.get();

        if (!(subsystem.hasArtifact() && distance != 0)) {
            subsystem.setVelocity(0);
            return;
        }


        double Vy = Math.sqrt(2 * Constants.G * Constants.MAX_HEIGHT);

        double t_num = Vy + Math.sqrt(Vy*Vy - 2 * Constants.G * Constants.DELTA_HEIGHT);
        double t = t_num / Constants.G;

        double vx = distance / t;
        double angle = Math.atan2(Vy, vx);
        double degrees = Math.toDegrees(angle);

        System.out.println("angle: "+ degrees);

        double velocity = Math.sqrt(Vy*Vy + vx*vx);

        subsystem.setVelocity(velocity*Constants.POWER_LAUNCHER_CONVERSION);
        subsystem.brake();


    }

    @Override
    public void end(boolean interrupted) {
        subsystem.setVelocity(0);
    }
}
