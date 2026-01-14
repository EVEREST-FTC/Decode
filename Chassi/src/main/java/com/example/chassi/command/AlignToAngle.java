package com.example.chassi.command;

import static com.everest.constants.Constants.CameraConstants.largeIncrementDistance;
import static com.everest.constants.Constants.CameraConstants.shortIncrementDistance;

import com.everest.CommandBased.definition.Command;
import com.example.chassi.MecanumDrive;

import com.everest.constants.PID;
import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.function.DoubleSupplier;

public class AlignToAngle extends Command {
    private final DoubleSupplier target;
    private final MecanumDrive chassi;

    final DoubleSupplier distanceSupplier;
    private final PID pid;

    private double alvo;
    private final double greatIncrement;

    final double shotincrement;

    final double LargeIncrement;
    Telemetry telemetry;

    public AlignToAngle(
            Telemetry telemetry, DoubleSupplier target, MecanumDrive chassi, DoubleSupplier distanceSupplier, PID pid, double alvo, double shortincrement, double largeIncrement) {
        this.target = target;
        this.chassi = chassi;
        this.distanceSupplier = distanceSupplier;
        this.pid = pid;
        this.greatIncrement = alvo;
        this.shotincrement = shortincrement;
        this.telemetry = telemetry;
        LargeIncrement = largeIncrement;
        this.alvo = greatIncrement;


        addRequirements(chassi);
    }

    @Override
    public void initialize() {
        pid.reset();
    }

    @Override
    public void execute() {
        if (distanceSupplier.getAsDouble() < shortIncrementDistance)
            alvo = shotincrement;
        else if (distanceSupplier.getAsDouble() > largeIncrementDistance)
            alvo = LargeIncrement;
        else
            alvo = greatIncrement;

        double angle = pid.calculate(alvo, target.getAsDouble());
        chassi.drive(0, 0, angle);
    }
/*

    @Override
    public boolean isFinished() {
        return pid.atSetpoint();
    }
*/

    @Override
    public void end(boolean interrupted) {
        chassi.drive(0, 0, 0);
    }
}
