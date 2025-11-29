package com.example.chassi;

import com.everest.CommandBased.essentials.Trigger;
import com.everest.CommandBased.util.InstantCommand;
import com.everest.constants.Constants;
import com.everest.constants.EnumTeam;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

public class RobotContainer {
    private final MecanumDrive chassi;
    private final Gamepad gamepad1;
    private final PID pid;

    private final Supplier<Double> distancia;

    double alvo;


    private final DoubleSupplier target;
    public RobotContainer(
            HardwareMap hardwareMap,
            Gamepad gamepad1,
            Telemetry telemetry,
            DoubleSupplier target,
            double alvo,
            Supplier<Double>distancia,
            EnumTeam team
    ) {
        this.gamepad1 = gamepad1;
        this.distancia = distancia;
        this.pid = new PID(Constants.KP, Constants.KI,Constants.KD);
        this.target = target;
        this.alvo = alvo;
        this.chassi = new MecanumDrive(
                hardwareMap,
                telemetry,
                team
        );

        robottest();
    }

    private void robottest(){
        new Trigger(()->gamepad1.left_bumper).toggleOnTrue(
                new AlignToAngle(target,chassi,()-> gamepad1.left_stick_x,()-> gamepad1.left_stick_y,distancia,pid,alvo)
                        .ateQUe(()->Math.abs(target.getAsDouble())<0.01)
        );
        new Trigger(()->gamepad1.right_bumper).toggleOnTrue(
                new InstantCommand(chassi::resetIMU)
        );
        new Trigger(()->gamepad1.y).toggleOnTrue(
                new UpRobot(chassi)
        );
        chassi.setDefaultCommand(new Drive(
                chassi,
                ()->gamepad1.right_stick_x*Constants.CHASSIS_LIMIT_POWER_TURN,
                ()->gamepad1.left_stick_x*Constants.CHASSIS_LIMIT_POWER,
                ()->gamepad1.left_stick_y*Constants.CHASSIS_LIMIT_POWER));


        }
    }

