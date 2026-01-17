package com.example.chassi;

import static com.everest.constants.Constants.ControllerConstants.GAMEPAD_AIM_TRIGGER;

import com.everest.CommandBased.essentials.Trigger;
import com.everest.CommandBased.util.InstantCommand;
import com.everest.constants.Constants.ControllerConstants;
import com.everest.constants.meta.EnumTeam;
import com.everest.constants.meta.RobotContainer;
import com.example.chassi.command.AlignToAngle;
import com.example.chassi.command.Drive;
import com.example.chassi.command.UpRobot;
import com.qualcomm.robotcore.hardware.Gamepad;

import java.util.function.DoubleSupplier;

import lombok.Builder;

@Builder
public class ChassisContainer implements RobotContainer {
    private final MecanumDrive chassis;
    private final Gamepad gamepad1;
    private final DoubleSupplier distance;

    private final EnumTeam team;

    private final DoubleSupplier target;

    public void mainRoutine(){
        new Trigger(()->gamepad1.right_bumper).whileTrue(
                new InstantCommand(chassis::resetIMU)
        );
        new Trigger(()->gamepad1.y).toggleOnTrue(new UpRobot(chassis));
        new Trigger(()->gamepad1.left_trigger> GAMEPAD_AIM_TRIGGER).whileTrue(
                new AlignToAngle(chassis.telemetry, target, chassis,
                        distance,
                        chassis.getPid(),
                        team.getIncrement(),
                        team.getShortIncrement(),
                        team.getLargeIncrement()
                )
        );

        chassis.setDefaultCommand(
                new Drive(
                        chassis,
                        () -> chassis.DeadZone(gamepad1.right_stick_x) * ControllerConstants.CHASSIS_LIMIT_POWER_TURN,
                        () -> chassis.DeadZone(gamepad1.left_stick_x) * ControllerConstants.CHASSIS_LIMIT_POWER,
                        () -> chassis.DeadZone(gamepad1.left_stick_y) * ControllerConstants.CHASSIS_LIMIT_POWER));
    }

}
