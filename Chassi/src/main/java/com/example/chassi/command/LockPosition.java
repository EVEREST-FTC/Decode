package com.example.chassi.command;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.everest.CommandBased.definition.Command;
import com.example.chassi.MecanumDrive;

public class LockPosition extends Command {
    private final MecanumDrive mecanumDrive;
    Pose2d pose2d;
    boolean isRunning = true;

    public LockPosition(MecanumDrive mecanumDrive) {
        this.mecanumDrive = mecanumDrive;

        addRequirements(mecanumDrive);
    }

    @Override
    public void initialize() {
        pose2d = mecanumDrive.localizer.getPose();
    }

    @Override
    public void execute() {
        mecanumDrive.actionBuilder(pose2d)
                .strafeToConstantHeading(pose2d.position)
                .build()
                .run(new TelemetryPacket());
    }
}
