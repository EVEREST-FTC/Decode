package com.everest.outtake.command;

import static com.everest.constants.Constants.PlatformConstants.CLOSE_POWER_LAUNCHER_CONVERSION;
import static com.everest.constants.Constants.PlatformConstants.FAR_POWER_LAUNCHER_CONVERSION;
import static com.everest.constants.Constants.PlatformConstants.POWER_LAUNCHER_CONVERSION;

import com.everest.CommandBased.definition.Command;
import com.everest.constants.Constants;
import com.everest.constants.Constants.CameraConstants;
import com.everest.outtake.subsystem.SubsystemOuttake;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

public class AutoLime3A extends Command {
    final Supplier<Double>distanceSupplier;
    final SubsystemOuttake subsystem;



    final double far, close, normal;
    DoubleSupplier increment;
    BooleanSupplier atsetpoint;
    double power;


    double velocity;

    private BooleanSupplier endSarcophagiCondition = ()->false;

    public AutoLime3A(Supplier<Double> distanceSupplier, SubsystemOuttake subsystem, double far, double close, double normal, BooleanSupplier atsetpoint, DoubleSupplier increment) {
        this(distanceSupplier, subsystem, far, close, normal, atsetpoint);
        this.increment = increment;
    }
    public AutoLime3A(Supplier<Double> distanceSupplier, SubsystemOuttake subsystem, double far, double close, double normal, BooleanSupplier atsetpoint) {
        this.distanceSupplier = distanceSupplier;
        this.subsystem = subsystem;
        this.atsetpoint = atsetpoint;
        this.far = far;
        this.close = close;
        this.normal = normal;
        increment = ()->0;
        addRequirements(subsystem);
    }
    public AutoLime3A(Supplier<Double> distanceSupplier, SubsystemOuttake subsystem, double far, double close, double normal, BooleanSupplier atsetpoint, BooleanSupplier endSarcophagiCondition) {
        this.distanceSupplier = distanceSupplier;
        this.subsystem = subsystem;
        this.atsetpoint = atsetpoint;
        this.far = far;
        this.close = close;
        this.normal = normal;
        increment = ()->0;
        this.endSarcophagiCondition = endSarcophagiCondition;
        addRequirements(subsystem);
    }

    @Override
    public void initialize() {
        /// definição de que posição ele está no momento de icialização



    }

    @Override
    public void execute() {
        if(endSarcophagiCondition.getAsBoolean()){
            subsystem.setVelocity(-100);
            return;
        }

        double distance = distanceSupplier.get();
        boolean atSetpoint = atsetpoint.getAsBoolean();
        if(distance< Constants.LauncherControllerConstants.DISTANCE_RANGE)
            power = close;
        else if(distance> Constants.CameraConstants.largeIncrementDistance)
            power = far;
        else
            power = normal;
        /// cauculo durante a execução do comando

        subsystem.setPower(power);
        double Vy = Math.sqrt(2 * Constants.CameraConstants.G * Constants.CameraConstants.MAX_HEIGHT);

        double t_num = Vy + Math.sqrt(Vy*Vy - 2 * Constants.CameraConstants.G * Constants.CameraConstants.DELTA_HEIGHT);
        double t = t_num / Constants.CameraConstants.G;

        double vx = distance / t;
        velocity = Math.sqrt(Vy*Vy + vx*vx);
        velocity*=(power+increment.getAsDouble());



        /*subsystem.setVelocity(velocity);*/

        if (!atSetpoint){
            subsystem.setVelocity(velocity);
        }



    }

    @Override
    public void end(boolean interrupted) {
        /// parada dos motores
        subsystem.setVelocity(0);
        subsystem.brake();
    }


}
