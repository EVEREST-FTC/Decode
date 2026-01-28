package com.everest.outtake.command;

import static com.everest.constants.Constants.PlatformConstants.CLOSE_POWER_LAUNCHER_CONVERSION;
import static com.everest.constants.Constants.PlatformConstants.FAR_POWER_LAUNCHER_CONVERSION;
import static com.everest.constants.Constants.PlatformConstants.POWER_LAUNCHER_CONVERSION;

import com.everest.CommandBased.definition.Command;
import com.everest.constants.Constants;
import com.everest.constants.Constants.CameraConstants;
import com.everest.outtake.subsystem.SubsystemOuttake;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.function.Supplier;

public class AutoLime3A extends Command {
    final Supplier<Double>distanceSupplier;
    final SubsystemOuttake subsystem;
    final double far, close, normal;
    double power;

    double velocity;
    public AutoLime3A(Supplier<Double> distanceSupplier, SubsystemOuttake subsystem, double far, double close, double normal) {
        this.distanceSupplier = distanceSupplier;
        this.subsystem = subsystem;
        this.far = far;
        this.close = close;
        this.normal = normal;
        addRequirements(subsystem);
    }

    @Override
    public void initialize() {

        double distance = distanceSupplier.get();
        if(distance< Constants.LauncherControllerConstants.DISTANCE_RANGE)
            power = close;
        else if(distance> Constants.CameraConstants.largeIncrementDistance)
            power = far;
        else
            power = normal;
        subsystem.setPower(power);
        double Vy = Math.sqrt(2 * Constants.CameraConstants.G * Constants.CameraConstants.MAX_HEIGHT);

        double t_num = Vy + Math.sqrt(Vy*Vy - 2 * Constants.CameraConstants.G * Constants.CameraConstants.DELTA_HEIGHT);
        double t = t_num / Constants.CameraConstants.G;

        double vx = distance / t;
        velocity = Math.sqrt(Vy*Vy + vx*vx);
        velocity*=power;
    }

    @Override
    public void execute() {


        subsystem.setVelocity(velocity);

    }

    @Override
    public void end(boolean interrupted) {
        subsystem.setVelocity(0);
        subsystem.brake();
    }
}
