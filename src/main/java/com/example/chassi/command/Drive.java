package com.example.chassi.command;

import com.example.chassi.Constants;
import com.example.chassi.subsystem.Subsystem;

import java.util.function.DoubleSupplier;

public class Drive extends com.everest.CommandBased.definition.Command {


    protected final Subsystem chassi;

    private final DoubleSupplier z;
    private final DoubleSupplier y;
    private final DoubleSupplier x;

    public Drive(Subsystem chassi, DoubleSupplier z, DoubleSupplier x, DoubleSupplier y) {
        this.chassi = chassi;

        this.z = z;
        this.y = y;
        this.x = x;

        addRequirements(chassi);
    }


    @Override
    public void execute() {
        double z = this.z.getAsDouble();
        double y = this.y.getAsDouble();
        double x = this.x.getAsDouble();
        chassi.drive(chassi.deadzone(x),chassi.deadzone(y),chassi.deadzone(z*Constants.Turnereduction));
    }

    @Override
    public void end(boolean interrupted) {
        chassi.brake();
    }
}

