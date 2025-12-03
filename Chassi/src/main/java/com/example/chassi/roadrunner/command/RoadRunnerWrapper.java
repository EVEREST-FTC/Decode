package com.example.chassi.roadrunner.command;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.everest.CommandBased.definition.Command;
import com.example.chassi.MecanumDrive;

import java.util.function.Consumer;
import java.util.function.Function;

public class RoadRunnerWrapper extends Command {
    private final MecanumDrive subsystem;
    boolean isrunning = true;
    Function<MecanumDrive, TrajectoryActionBuilder> action;
    Action runningAction;
    final TelemetryPacket telemetryPacket;
    public RoadRunnerWrapper(MecanumDrive subsystem,  Function<MecanumDrive, TrajectoryActionBuilder> action) {
        this.subsystem = subsystem;
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
        isrunning = runningAction
                .run(telemetryPacket);
    }

    @Override
    public boolean isFinished() {
        return !isrunning;
    }
}
