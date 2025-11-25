package com.everest.outtake;

import com.everest.CommandBased.essentials.Trigger;
import com.everest.outtake.command.AutoLime3A;
import com.everest.outtake.command.LaunchCommand;
import com.everest.outtake.subsystem.SubsystemOuttake;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcontroller.external.samples.SensorColor;
import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.function.Supplier;

public class RobotContainer {
    private final Gamepad gamepad1;

    private final Supplier<Double>distancia;

    private final SubsystemOuttake subsystem;

    public RobotContainer(
            HardwareMap hardwareMap,
            Gamepad gamepad1,
            Telemetry telemetry,
            Supplier<Double>distancia,
            SubsystemOuttake subsystem

    ) {
        this.gamepad1 = gamepad1;
        this.distancia = distancia;
        this.subsystem = subsystem;

        robottest();
    }

    private void robottest(){
        new Trigger(()->gamepad1.b).toggleOnTrue(
               new AutoLime3A(distancia,subsystem)
        );
    }
    private void triggerSelection(){


        new Trigger(()->gamepad1.y).toggleOnTrue(
                new LaunchCommand(subsystem, 0.85)
        );
        new Trigger(()->gamepad1.a).toggleOnTrue(
                new LaunchCommand(subsystem, 0.90)
        );
        new Trigger(()->gamepad1.x).toggleOnTrue(
                new LaunchCommand(subsystem, 1)
        );


    }

}
