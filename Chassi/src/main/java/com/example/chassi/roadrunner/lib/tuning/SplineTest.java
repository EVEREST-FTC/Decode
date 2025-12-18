package com.example.chassi.roadrunner.lib.tuning;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.everest.constants.meta.EnumTeam;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import com.example.chassi.roadrunner.lib.MecanumDrive;
import com.example.chassi.roadrunner.lib.TankDrive;

public final class SplineTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d beginPose = new Pose2d(0, 0, 0);
        MecanumDrive drive = new com.example.chassi.MecanumDrive(hardwareMap, telemetry, EnumTeam.BLUE);

        waitForStart();

        Actions.runBlocking(
                drive.actionBuilder(beginPose)
                        .strafeToLinearHeading(new Vector2d(20, -20), Math.PI )
                        .strafeToLinearHeading(new Vector2d(10, -40), Math.PI/3)
                        .strafeToLinearHeading(new Vector2d(30, -50), Math.PI/5)
                        .strafeToLinearHeading(new Vector2d(50, -60), Math.PI/5)
                        .strafeToLinearHeading(new Vector2d(0, 0), Math.PI )
                        .build());
    }
}
  /*Actions.runBlocking(
                drive.actionBuilder(beginPose)
                        .splineTo(new Vector2d(30, 30), Math.PI / 2)
                        .splineTo(new Vector2d(0, 60), Math.PI)
                        .build());*/