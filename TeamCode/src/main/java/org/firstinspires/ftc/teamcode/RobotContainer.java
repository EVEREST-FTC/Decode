package org.firstinspires.ftc.teamcode;

import com.everest.constants.meta.EnumTeam;
import com.everest.intake.IntakeContainer;
import com.everest.intake.Subsystem.SubsytemIntake;
import com.everest.outtake.OuttakeContainer;
import com.everest.outtake.subsystem.SubsystemOuttake;
import com.everest.plataform.PlatformContainer;
import com.everest.plataform.subsystem.SubsystemCalibrator;
import com.everest.trigger.TriggerContainer;
import com.everest.trigger.subsystem.TriggerSubsystem;
import com.example.chassi.MecanumDrive;
import com.example.chassi.ChassisContainer;
import com.example.gate.GateContainer;
import com.example.gate.SubsystemGate;
import com.example.limelightcentral.Subsystem;
import com.example.sarcofogo.SarcofogoContainer;
import com.example.sarcofogo.SubsystemSarcofogo;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import lombok.Builder;

@Builder
public class RobotContainer implements com.everest.constants.meta.RobotContainer {
    Gamepad gamepad1;
    HardwareMap hardwareMap;
    Telemetry telemetry;
    EnumTeam team;

    @Override
    public void mainRoutine() {

        MecanumDrive chassis = new MecanumDrive(hardwareMap,
                telemetry,
                team);
        SubsystemGate gate = new SubsystemGate(hardwareMap,
                telemetry);
        SubsystemSarcofogo sarcofogo = new SubsystemSarcofogo(hardwareMap,
                telemetry);
        SubsystemOuttake outtake = new SubsystemOuttake(hardwareMap,
                telemetry);
        SubsystemCalibrator platform =new SubsystemCalibrator(hardwareMap,
                telemetry);
        TriggerSubsystem triggerSubsystem = new TriggerSubsystem(hardwareMap,
                telemetry);
        SubsytemIntake intake = new SubsytemIntake(hardwareMap,
                telemetry);

        Subsystem limelight = new Subsystem(hardwareMap,
                telemetry,
                team,
                chassis::getYaw
                );
        ChassisContainer.builder()
                .team(team)
                .target(limelight::getTx)
                .distancia(limelight::getfrontal)
                .gamepad1(gamepad1)
                .chassi(chassis)
                .build()
                .defineMainRoutine();


        GateContainer.builder()
                .subsystemGate(gate)
                .hasArtifact(outtake::hasArtifact)
                .gamepad(gamepad1)
                .build()
                .defineMainRoutine();

        IntakeContainer.builder()
                .gamepad(gamepad1)
                .subsytemIntake(intake)
                .build()
                .defineMainRoutine();


        OuttakeContainer.builder()
                .gamepad1(gamepad1)
                .distancia(limelight::getfrontal)
                .subsystem(outtake)
                .build()
                .defineMainRoutine();

        PlatformContainer.builder()
                .gamepad(gamepad1)
                .subsystemCalibrator(platform)
                .distancia(limelight::getfrontal)
                .hasArtifact(outtake::hasArtifact)
                .telemetry(telemetry)
                .build()
                .defineMainRoutine();


        SarcofogoContainer.builder()
                .subsystemSarcofogo(sarcofogo)
                .gamepad(gamepad1)
                .hasArtifact(outtake::hasArtifact)
                .build()
                .defineMainRoutine()
        ;

        TriggerContainer.builder()
                .chassisPid(chassis::atSetpoint)
                .hasartifact(outtake::hasArtifact)
                .gamepad(gamepad1)
                .triggerSubsystem(triggerSubsystem)
                .velocityVerifier(outtake::atSetpoint)
                .build()
                .defineMainRoutine();
    }
}
