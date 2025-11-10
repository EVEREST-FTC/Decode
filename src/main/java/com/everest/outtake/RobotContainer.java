package com.everest.outtake;

import com.everest.CommandBased.essentials.Trigger;
import com.everest.outtake.command.LaunchCommand;
import com.everest.outtake.subsystem.Subsystem;
import com.everest.outtake.subsystem.SubsystemCalibrator;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class RobotContainer {
    private final Subsystem subsystem;

    private final SubsystemCalibrator subsystemCalibrator;

    private final Gamepad gamepad1;

    public RobotContainer(
            HardwareMap hardwareMap,
            Gamepad gamepad1,
            Telemetry telemetry
    ) {
        this.gamepad1 = gamepad1;
        this.subsystem = new Subsystem(
                hardwareMap,
                telemetry
        );
        this.subsystemCalibrator = new SubsystemCalibrator(hardwareMap,telemetry);

        triggerSelection();
    }

    private void triggerSelection(){

        new Trigger(()->gamepad1.b).toggleOnTrue(
                new LaunchCommand(subsystem, 0.85)
        );
        new Trigger(()->gamepad1.a).toggleOnTrue(
                new LaunchCommand(subsystem, 0.75)
        );
        new Trigger(()->gamepad1.a).toggleOnTrue(
                new LaunchCommand(subsystem, 0.75)
        );

    }

}
