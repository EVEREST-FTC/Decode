package org.firstinspires.ftc.teamcode;

import com.everest.constants.meta.EnumTeam;
import com.everest.constants.meta.RobotContainer;
import com.everest.intake.Subsystem.SubsytemIntake;
import com.everest.outtake.subsystem.SubsystemOuttake;
import com.everest.plataform.subsystem.SubsystemCalibrator;
import com.everest.trigger.subsystem.TriggerSubsystem;
import com.example.chassi.MecanumDrive;
import com.example.gate.SubsystemGate;
import com.example.limelightcentral.Subsystem;
import com.example.sarcofogo.SubsystemSarcofogo;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import lombok.Builder;
/// Utilizacao do subssitema autonomo, criando so recursos necessarios para sua rotina
@Builder
public class AutonomousContainer implements RobotContainer {

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

        AutonomousOptimized.builder()
                .subLime(limelight)
                .subsystemCalibrator(platform)
                .subsystemGate(gate)
                .subsystemSarcofogo(sarcofogo)
                .subsystemOuttake(outtake)
                .intake(intake)
                .telemetry(telemetry)
                .team(team)
                .chassi(chassis)
                .triggerSubsystem(triggerSubsystem)
                .build()
                .mainRoutine();

    }
}
