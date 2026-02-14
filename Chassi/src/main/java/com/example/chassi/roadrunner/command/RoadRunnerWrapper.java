package com.example.chassi.roadrunner.command;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.everest.CommandBased.definition.Command;
import com.example.chassi.MecanumDrive;

import java.util.function.Function;

public class RoadRunnerWrapper extends Command {
    private final MecanumDrive subsystem;
    boolean isRunning = true;
    Function<MecanumDrive, TrajectoryActionBuilder> action;
    Action runningAction;
    final TelemetryPacket telemetryPacket;
    private final Pose2d lastPose;
    public RoadRunnerWrapper(MecanumDrive subsystem, Function<MecanumDrive, TrajectoryActionBuilder> action, Pose2d lastPose) {
        this.subsystem = subsystem;
        this.lastPose = lastPose;
        this.telemetryPacket = new TelemetryPacket();
        this.action = action;
        addRequirements(subsystem);
    }

    @Override
    public void initialize() {
        runningAction = action.apply(subsystem)
                .build();
    }

    @Override
    public void execute() {
        isRunning = runningAction
                .run(telemetryPacket);
    }

    @Override
    public void end(boolean interrupted) {
        subsystem.drive(0,0,0);
        subsystem.setLastPose(lastPose);
    }

    @Override
    public boolean isFinished() {
        return !isRunning;
    }
}
