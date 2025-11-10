package com.example.chassi;

import com.example.chassi.command.Drive;
import com.example.chassi.subsystem.Subsystem;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.function.DoubleSupplier;

public class RobotContainer {
    public RobotContainer(HardwareMap hardwareMap,
                          Telemetry telemetry,
                          Gamepad gamepad) {
        Subsystem subsystem = new Subsystem(hardwareMap, telemetry);
        DoubleSupplier frontalController = ()->gamepad.left_stick_y,
                        strafeController = ()->gamepad.left_stick_x,
                        turnController = ()->gamepad.right_stick_x;
        subsystem.setDefaultCommand(
                new Drive(
                        subsystem,
                        turnController,
                        frontalController,
                        strafeController
                )
        );
    }
}
