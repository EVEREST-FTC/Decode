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

import java.util.Map;
import java.util.function.DoubleSupplier;

import lombok.Builder;

@Builder
public class ChassisContainer implements RobotContainer {
    private final MecanumDrive chassi;
    private final Gamepad gamepad1;
    private final DoubleSupplier distancia;

    private final EnumTeam team;

    private final DoubleSupplier target;

    public void mainRoutine(){
        new Trigger(()->gamepad1.right_bumper).whileTrue(
                new InstantCommand(chassi::resetIMU)
        );
        new Trigger(()->gamepad1.y).toggleOnTrue(new UpRobot(chassi));
        new Trigger(()->gamepad1.left_trigger> GAMEPAD_AIM_TRIGGER).whileTrue(
                new AlignToAngle(chassi.telemetry, target, chassi,
                        distancia,
                        chassi.getPid(),
                        team.getIncrement(),
                        team.getShortIncrement(),
                        team.getLargeIncrement()
                )
        );

        chassi.setDefaultCommand(
                new Drive(
                        chassi,
                        () -> chassi.DeadZone(gamepad1.right_stick_x) * ControllerConstants.CHASSIS_LIMIT_POWER_TURN,
                        () -> chassi.DeadZone(gamepad1.left_stick_x) * ControllerConstants.CHASSIS_LIMIT_POWER,
                        () -> chassi.DeadZone(gamepad1.left_stick_y) * ControllerConstants.CHASSIS_LIMIT_POWER));
    }

}
