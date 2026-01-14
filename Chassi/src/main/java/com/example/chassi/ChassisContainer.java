package com.example.chassi;

import com.everest.CommandBased.compositions.ParallelCommandGroup;
import com.everest.CommandBased.compositions.SelectCommand;
import com.everest.CommandBased.compositions.SequentialCommandGroup;
import com.everest.CommandBased.definition.Command;
import com.everest.CommandBased.essentials.Trigger;
import com.everest.CommandBased.util.InstantCommand;
import com.everest.constants.Constants;
import com.everest.constants.meta.EnumTeam;
import com.everest.constants.meta.RobotContainer;
import com.everest.constants.meta.RobotState;
import com.everest.constants.meta.StateMachine;
import com.example.chassi.command.AlignToAngle;
import com.example.chassi.command.Drive;
import com.example.chassi.command.LockPosition;
import com.example.chassi.command.UpRobot;
import com.example.chassi.roadrunner.command.RoadRunnerWrapper;
import com.qualcomm.robotcore.hardware.Gamepad;

import java.util.Map;
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
        new Trigger(()->gamepad1.right_bumper).toggleOnTrue(
                new InstantCommand(chassi::resetIMU)
        );
        new Trigger(()->gamepad1.y).toggleOnTrue(new UpRobot(chassi));
        new Trigger(()->gamepad1.left_trigger>0.9).whileTrue(
                Constants.setState(RobotState.Mirando)
        );
        new Trigger(()->gamepad1.left_trigger>0.9).whileFalse(
                Constants.setState(RobotState.Dirigindo)
        );
    }

    public void states(){
        chassi.setDefaultCommand(
            new SelectCommand<>(
                    Map.ofEntries(
                            Map.entry(
                                    RobotState.Dirigindo, new Drive(
                                            chassi,
                                            () -> chassi.DeadZone(gamepad1.right_stick_x) * Constants.CHASSIS_LIMIT_POWER_TURN,
                                            () -> chassi.DeadZone(gamepad1.left_stick_x) * Constants.CHASSIS_LIMIT_POWER,
                                            () -> chassi.DeadZone(gamepad1.left_stick_y) * Constants.CHASSIS_LIMIT_POWER)),
                            Map.entry(
                                    RobotState.Atirando, new AlignToAngle(chassi.telemetry, target, chassi,
                                            distancia,
                                            chassi.getPid(),
                                            team.getIncrement(),
                                            team.getShortIncrement(),
                                            team.getLargeIncrement()
                                    )
                            )),
                    ()->Constants.state));
    }

}
