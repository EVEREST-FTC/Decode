package com.everest.plataform.command;

import com.everest.CommandBased.definition.Command;
import com.everest.constants.Constants;
import com.everest.plataform.subsystem.SubsystemCalibrator;


import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.function.Supplier;

public class AutoLime3AC extends Command {
    final Supplier<Double>distanceSupplier;
    final SubsystemCalibrator subsystem;

    final Telemetry telemetry;

    public AutoLime3AC(Supplier<Double> distancia, SubsystemCalibrator subsystem, Telemetry telemetry) {
        distanceSupplier = distancia;
        this.subsystem = subsystem;
        this.telemetry = telemetry;
        addRequirements(subsystem);
    }

    @Override
    public void execute() {
        double distance = distanceSupplier.get();

        double Vy = Math.sqrt(2 * Constants.G * Constants.MAX_HEIGHT);

        double t_num = Vy + Math.sqrt(Vy*Vy - 2 * Constants.G * Constants.DELTA_HEIGHT);
        double t = t_num / Constants.G;

        double vx = distance / t;
        double angle = Math.atan2(Vy, vx);
        double degrees = Math.toDegrees(angle);
        subsystem.setPositionL(degrees);
    }


}
