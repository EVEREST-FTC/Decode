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
    private final MecanumDrive chassis;
    private final DoubleSupplier distanceSupplier;
    private final PID pid;
    private double alvo;
    private final double greatIncrement;
    private final double shotincrement;
    private final double LargeIncrement;
    Telemetry telemetry;

    public AlignToAngle( /// este é o comando que usa a classe PID para alinhar o angulo do robo em relação ao um alvo
            Telemetry telemetry, DoubleSupplier target, MecanumDrive chassi, DoubleSupplier distanceSupplier, PID pid, double alvo, double shortincrement, double largeIncrement) {
        this.target = target;
        this.chassis = chassi;
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
    /// na inicialização o comando reseta o valor do PID
    public void initialize() {
        pid.reset();
    }

    @Override
    /// durante a execução o comando usa o resultado gerado pela class PID e transforma em potencias para o movimento de rotação
    public void execute() {
        if (distanceSupplier.getAsDouble() < shortIncrementDistance)
            alvo = shotincrement;
        else if (distanceSupplier.getAsDouble() > largeIncrementDistance)
            alvo = LargeIncrement;
        else
            alvo = greatIncrement;

        double angle = pid.calculate(alvo, target.getAsDouble());
        chassis.drive(0, 0, angle);
    }

    @Override
    /// na finalização do comando ele para totalmente o potenica do motor
    public void end(boolean interrupted) {
        chassis.drive(0, 0, 0);
    }
}
