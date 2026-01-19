package com.example.chassi.command;

import com.everest.CommandBased.definition.Command;
import com.example.chassi.MecanumDrive;

import java.util.function.DoubleSupplier;

public class Drive extends Command {
    final MecanumDrive chassis;
    private final DoubleSupplier z;
    private final DoubleSupplier y;
    private final DoubleSupplier x;
    public Drive(MecanumDrive chassis, DoubleSupplier z, DoubleSupplier x, DoubleSupplier y) {
        this.chassis = chassis;
        this.z = z;
        this.y = y;
        this.x = x;
        addRequirements(chassis);
    }


    @Override
    public void execute() {
        double z = this.z.getAsDouble();
        double y = this.y.getAsDouble();
        double x = this.x.getAsDouble();
        chassis.brake();
        chassis.driveFieldRelative(y,-x,-z);
    }

    @Override
    public void end(boolean interrupted) {
            chassis.brake();
    }

}
