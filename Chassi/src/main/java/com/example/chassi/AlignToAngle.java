package com.example.chassi;

import com.everest.CommandBased.definition.Command;

import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

public class AlignToAngle extends Command {
    private final DoubleSupplier target;
    private final Chassi chassi;
    private final DoubleSupplier y;
    private final DoubleSupplier x;

    final Supplier<Double>distanceSupplier;
    private final PID pid;

    private double alvo;

    public AlignToAngle(DoubleSupplier target, Chassi chassi, DoubleSupplier x, DoubleSupplier y, Supplier<Double> distanceSupplier, PID pid, double alvo) {
        this.target = target;
        this.chassi = chassi;
        this.y = y;
        this.x = x;
        this.distanceSupplier = distanceSupplier;
        this.pid = pid;
        this.alvo = alvo;

        addRequirements(chassi);
    }

    @Override
    public void initialize() {
        pid.reset();
    }

    @Override
    public void execute() {
        if (distanceSupplier.get() < 1.79)
            alvo = 0;

        double angle = pid.calculate(alvo, target.getAsDouble());
        double x = this.x.getAsDouble();
        double y = this.y.getAsDouble();

        chassi.drive(y, -x, angle);
    }
}
