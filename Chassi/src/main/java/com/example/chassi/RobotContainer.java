package com.example.chassi;

import com.everest.CommandBased.compositions.SelectCommand;
import com.everest.CommandBased.essentials.Trigger;
import com.everest.CommandBased.util.InstantCommand;
import com.everest.constants.Constants;
import com.everest.constants.EnumTeam;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.Map;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

public class RobotContainer {
    private final MecanumDrive chassi;
    private final Gamepad gamepad1;
    private final Supplier<Double> distancia;

    double alvo;

    double shotincrement;


    private final DoubleSupplier target;

    public RobotContainer(
            Gamepad gamepad1,
            MecanumDrive chassi,
            DoubleSupplier target,
            Supplier<Double> distancia,
            EnumTeam team
    ) {
        this.gamepad1 = gamepad1;
        this.distancia = distancia;
        this.target = target;
        this.alvo = team.getIncrement();
        this.shotincrement = team.getShortIncrement();
        this.chassi = chassi;

        chassi.setDefaultCommand(new Drive(
                chassi,
                ()->chassi.DeadZone(gamepad1.right_stick_x)*Constants.CHASSIS_LIMIT_POWER_TURN,
                ()->chassi.DeadZone(gamepad1.left_stick_x)*Constants.CHASSIS_LIMIT_POWER,
                ()->chassi.DeadZone(gamepad1.left_stick_y)*Constants.CHASSIS_LIMIT_POWER));
        automationTest();
    }

    private void automationTest() {
        new Trigger(() -> gamepad1.right_bumper).toggleOnTrue(
                new InstantCommand(chassi::resetIMU)
        );
        new Trigger(() -> gamepad1.y).toggleOnTrue(
                new UpRobot(chassi)
        );
        new Trigger(() -> gamepad1.left_bumper).toggleOnTrue(
                new SelectCommand<>(
                        Map.ofEntries(
                                Map.entry(
                                        ChassisState.AIMING, new AlignToAngle(target, chassi,
                                                () -> gamepad1.left_stick_x,
                                                () -> gamepad1.left_stick_y,
                                                distancia,
                                                chassi.getPid(),
                                                alvo,
                                                shotincrement
                                        )
                                ),
                                Map.entry(
                                        ChassisState.DRIVING, new Drive(
                                                chassi,
                                                () -> chassi.DeadZone(gamepad1.right_stick_x) * Constants.CHASSIS_LIMIT_POWER_TURN,
                                                () -> chassi.DeadZone(gamepad1.left_stick_x) * Constants.CHASSIS_LIMIT_POWER,
                                                () -> chassi.DeadZone(gamepad1.left_stick_y) * Constants.CHASSIS_LIMIT_POWER)
                                )
                        ),
                        () -> ChassisState.selector(Math.abs(target.getAsDouble()) > 0.01)
                )
        );
    }

    private void robottest() {

        new Trigger(() -> gamepad1.left_bumper).toggleOnTrue(
                new AlignToAngle(target, chassi,
                        () -> gamepad1.left_stick_x,
                        () -> gamepad1.left_stick_y,
                        distancia,
                        chassi.getPid(),
                        alvo,
                        shotincrement
                )
                        .ateQUe(() -> Math.abs(target.getAsDouble()) < 0.01)
        );
        new Trigger(() -> gamepad1.right_bumper).toggleOnTrue(
                new InstantCommand(chassi::resetIMU)
        );
        new Trigger(() -> gamepad1.y).toggleOnTrue(
                new UpRobot(chassi)
        );
    }
}

