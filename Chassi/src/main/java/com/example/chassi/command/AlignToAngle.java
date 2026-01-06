package com.example.chassi.command;

import com.acmerobotics.dashboard.FtcDashboard;
import com.everest.CommandBased.definition.Command;
import com.example.chassi.MecanumDrive;

import com.everest.constants.PID;
import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

public class AlignToAngle extends Command {
    private final DoubleSupplier target;
    private final MecanumDrive chassi;

    final DoubleSupplier distanceSupplier;
    private final PID pid;

    private double alvo;

    final double shotincrement;
    Telemetry telemetry;

    public AlignToAngle(
            Telemetry telemetry, DoubleSupplier target, MecanumDrive chassi, DoubleSupplier distanceSupplier, PID pid, double alvo,double shortincrement) {
        this.target = target;
        this.chassi = chassi;
        this.distanceSupplier = distanceSupplier;
        this.pid = pid;
        this.alvo = alvo;
        this.shotincrement = shortincrement;
        this.telemetry = telemetry;


        addRequirements(chassi);
    }

    @Override
    public void initialize() {
        pid.reset();
    }

    @Override
    public void execute() {
        if (distanceSupplier.getAsDouble() < 1.79)
            alvo = shotincrement;

        double angle = pid.calculate(alvo, target.getAsDouble());
        telemetry.addData("Angle error", pid.atSetpoint());
        chassi.drive(0, 0, angle);
    }

    @Override
    public boolean isFinished() {
        return pid.atSetpoint();
    }

    @Override
    public void end(boolean interrupted) {
        chassi.drive(0, 0, 0);
    }
}
