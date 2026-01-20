package com.everest.outtake.command;

import static com.everest.constants.Constants.PlatformConstants.CLOSE_POWER_LAUNCHER_CONVERSION;
import static com.everest.constants.Constants.PlatformConstants.FAR_POWER_LAUNCHER_CONVERSION;
import static com.everest.constants.Constants.PlatformConstants.POWER_LAUNCHER_CONVERSION;

import com.everest.CommandBased.definition.Command;
import com.everest.constants.Constants;
import com.everest.constants.Constants.CameraConstants;
import com.everest.outtake.subsystem.SubsystemOuttake;

import java.util.function.Supplier;

public class AutoLime3A extends Command {
    final Supplier<Double>distanceSupplier;
    final SubsystemOuttake subsystem;
    final double far, close, normal;
    public AutoLime3A(Supplier<Double> distanceSupplier, SubsystemOuttake subsystem) {
        this(distanceSupplier, subsystem, FAR_POWER_LAUNCHER_CONVERSION, CLOSE_POWER_LAUNCHER_CONVERSION, POWER_LAUNCHER_CONVERSION);
    }
    public AutoLime3A(Supplier<Double> distanceSupplier, SubsystemOuttake subsystem, double far, double close, double normal) {
        this.distanceSupplier = distanceSupplier;
        this.subsystem = subsystem;
        this.far = far;
        this.close = close;
        this.normal = normal;
        addRequirements(subsystem);
    }

    @Override
    public void execute() {

        double distance = distanceSupplier.get();
        double Vy = Math.sqrt(2 * CameraConstants.G * CameraConstants.MAX_HEIGHT);

        double t_num = Vy + Math.sqrt(Vy*Vy - 2 * CameraConstants.G * CameraConstants.DELTA_HEIGHT);
        double t = t_num / CameraConstants.G;

        double vx = distance / t;
        double velocity = Math.sqrt(Vy*Vy + vx*vx);
        if(distance< Constants.LauncherControllerConstants.DISTANCE_RANGE)
            velocity*=close;
        else if(distance> CameraConstants.largeIncrementDistance)
            velocity*=far;
        else
            velocity*=normal;

        subsystem.setVelocity(velocity);
    }

    @Override
    public void end(boolean interrupted) {
        subsystem.setVelocity(0);
        subsystem.brake();
    }
}
