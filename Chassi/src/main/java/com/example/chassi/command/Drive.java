package com.example.chassi.command;

import com.everest.CommandBased.definition.Command;
import com.example.chassi.MecanumDrive;

import java.util.function.DoubleSupplier;

public class Drive extends Command {
    final MecanumDrive chassis;
    private final DoubleSupplier z;
    private final DoubleSupplier y;
    private final DoubleSupplier x;

    /// Este é o comando de dirigir que vai contralar a movimentação do robo
    public Drive(MecanumDrive chassis, DoubleSupplier z, DoubleSupplier x, DoubleSupplier y) {
        this.chassis = chassis;
        this.z = z;
        this.y = y;
        this.x = x;
        addRequirements(chassis);
    }


    @Override
    public void execute() {
        /// durante a execução do comando ele recebe os valores de entrada que serão do gamepad e aplica nos metodos do subsistema
        double z = this.z.getAsDouble();
        double y = this.y.getAsDouble();
        double x = this.x.getAsDouble();
        chassis.brake();
        chassis.driveFieldRelative(y,-x,-z);
    }

    @Override
    /// na finalização do commando ele para todos os motores
    public void end(boolean interrupted) {
            chassis.brake();
    }

}
