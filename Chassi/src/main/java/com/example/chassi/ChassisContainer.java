package com.example.chassi;

import com.everest.CommandBased.compositions.ParallelCommandGroup;
import com.everest.CommandBased.compositions.SequentialCommandGroup;
import com.everest.CommandBased.definition.Command;
import com.everest.CommandBased.essentials.Trigger;
import com.everest.CommandBased.util.InstantCommand;
import com.everest.constants.Constants;
import com.everest.constants.meta.EnumTeam;
import com.everest.constants.meta.RobotContainer;
import com.everest.constants.meta.StateMachine;
import com.example.chassi.command.AlignToAngle;
import com.example.chassi.command.Drive;
import com.example.chassi.command.LockPosition;
import com.example.chassi.roadrunner.command.RoadRunnerWrapper;
import com.qualcomm.robotcore.hardware.Gamepad;

import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

import lombok.Builder;

@Builder
public class ChassisContainer implements RobotContainer {
    private final MecanumDrive chassi;
    private final Gamepad gamepad1;
    private final DoubleSupplier distancia;

    private final EnumTeam team;

    private final DoubleSupplier target;
    private final StateMachine stateMachine = new StateMachine(ChassisState.DRIVING);

    public void mainRoutine(){
        chassi.setDefaultCommand(
                new Drive(
                        chassi,
                        () -> chassi.DeadZone(gamepad1.right_stick_x) * Constants.CHASSIS_LIMIT_POWER_TURN,
                        () -> chassi.DeadZone(gamepad1.left_stick_x) * Constants.CHASSIS_LIMIT_POWER,
                        () -> chassi.DeadZone(gamepad1.left_stick_y) * Constants.CHASSIS_LIMIT_POWER));
        new Trigger(()->gamepad1.right_bumper).toggleOnTrue(
                new InstantCommand(chassi::resetIMU)
        );
        new Trigger(()->gamepad1.left_trigger>0.9).whileTrue(
                new SequentialCommandGroup(
                        new AlignToAngle(chassi.telemetry, target, chassi,
                                distancia,
                                chassi.getPid(),
                                team.getIncrement(),
                                team.getShortIncrement()
                        ),
                        new LockPosition(chassi)
                )
        );

    }


    public void states(){
       /* ChassisState.DRIVING.setAssociatedCommand(
                new Drive(
                        chassi,
                        () -> chassi.DeadZone(gamepad1.right_stick_x) * Constants.CHASSIS_LIMIT_POWER_TURN,
                        () -> chassi.DeadZone(gamepad1.left_stick_x) * Constants.CHASSIS_LIMIT_POWER,
                        () -> chassi.DeadZone(gamepad1.left_stick_y) * Constants.CHASSIS_LIMIT_POWER)
        );

        ChassisState.AIMING.setAssociatedCommand(
                new AlignToAngle(target, chassi,
                        () -> gamepad1.left_stick_x,
                        () -> gamepad1.left_stick_y,
                        distancia,
                        chassi.getPid(),
                        team.getIncrement(),
                        team.getShortIncrement()
                )
        );
        stateMachine.createRelation(ChassisState.DRIVING, ChassisState.AIMING, new InstantCommand());
        stateMachine.createRelation(ChassisState.AIMING, ChassisState.DRIVING, new InstantCommand());
        stateMachine.setCurrentState(ChassisState.DRIVING);*/
    }

}
