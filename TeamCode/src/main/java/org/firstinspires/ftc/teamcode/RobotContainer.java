package org.firstinspires.ftc.teamcode;

import com.everest.constants.EnumTeam;
import com.everest.outtake.subsystem.SubsystemOuttake;
import com.example.gate.SubsystemGate;
import com.example.limelightcentral.Subsystem;
import com.example.sarcofogo.SubsystemSarcofogo;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class RobotContainer {
    public RobotContainer(
            HardwareMap hardwareMap,
            Telemetry telemetry,
            Gamepad gamepad1,
            Gamepad gamepad2,
            EnumTeam team
    ) {
        Subsystem subsystem = new Subsystem(hardwareMap,telemetry,team);
        SubsystemOuttake subsystemOuttake = new SubsystemOuttake(hardwareMap, telemetry);
        SubsystemGate subsystemGate = new SubsystemGate(hardwareMap,telemetry);
        SubsystemSarcofogo subsystemSarcofogo = new SubsystemSarcofogo(hardwareMap,telemetry);
        new com.everest.plataform.RobotContainer(
                hardwareMap,
                gamepad1,
                telemetry,
                subsystem::getfrontal);
        //Especifica o gampead para o módulo
        new com.example.chassi.RobotContainer(
                hardwareMap,
                gamepad1,
                telemetry,
                subsystem::getTx,
                subsystem.getIdtag(),
                subsystem::getfrontal,
                team
        );
        //especifica o gamepad1 para o módulo
        new com.everest.outtake.RobotContainer(
                hardwareMap,
                gamepad1,
                telemetry,
                subsystem::getfrontal,
                subsystemOuttake
        );
        new com.everest.intake.RobotContainer(
                hardwareMap,
                gamepad1,
                telemetry
        );
        new com.everest.trigger.RobotContainer(
                hardwareMap,
                telemetry,
                gamepad1,
                subsystemOuttake::atSetpoint
        );
        new com.example.sarcofogo.RobotContainer(
                hardwareMap,
                telemetry,
                gamepad1,
                subsystemSarcofogo
        );
        new com.example.gate.RobotContainer(
                hardwareMap,
                telemetry,
                gamepad1,
                subsystemGate,
                subsystemOuttake::hasArtifact
        );
    }
}
