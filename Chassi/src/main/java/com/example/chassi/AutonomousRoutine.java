package com.example.chassi;

import com.acmerobotics.roadrunner.Pose2d;
import com.everest.constants.Constants;
import com.everest.constants.EnumTeam;
import com.example.chassi.roadrunner.command.RoadRunnerWrapper;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

public class AutonomousRoutine {
    private final MecanumDrive chassi;
    public AutonomousRoutine(
            HardwareMap hardwareMap,
            Telemetry telemetry,
            EnumTeam team
    ) {
        this.chassi = new MecanumDrive(
                hardwareMap,
                telemetry,
                team
        );
        auto();
    }

    public void  auto(){
        new RoadRunnerWrapper(chassi,new Pose2d(10,0,Math.PI/4),
                c->c.actionBuilder(chassi.localizer.getPose()).lineToX(2)).schedule();
    }
}
