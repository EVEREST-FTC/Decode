package org.firstinspires.ftc.teamcode;

import static com.everest.constants.Constants.GyroConstants.KD_TELEOP;
import static com.everest.constants.Constants.GyroConstants.KI_TELEOP;
import static com.everest.constants.Constants.GyroConstants.KP_TELEOP;

import com.everest.CommandBased.essentials.Trigger;
import com.everest.CommandBased.util.InstantCommand;
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
import com.example.sarcofogo.FlagSubsystem;
import com.example.sarcofogo.SarcophagiContainer;
import com.example.sarcofogo.SubsystemSarcofogo;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Supplier;
import org.firstinspires.ftc.robotcore.external.Telemetry;

import lombok.Builder;

@Builder
public class RobotContainer implements com.everest.constants.meta.RobotContainer {
    private Gamepad gamepad1;
    private Gamepad gamepad2;
    private HardwareMap hardwareMap;
    private Telemetry telemetry;
    private EnumTeam team;
    private Supplier<Gamepad> gamepadSupplier;

    @Override
    public void mainRoutine() {
        /// subsitemas iniciados
        MecanumDrive chassis = new MecanumDrive(hardwareMap,
                telemetry,
                team,
                KP_TELEOP,
                KI_TELEOP,
                KD_TELEOP
                );
        SubsystemGate gate = new SubsystemGate(hardwareMap,
                telemetry);
        SubsystemSarcofogo sarcophagi = new SubsystemSarcofogo(hardwareMap,
                telemetry);
        FlagSubsystem flagSubsystem = new FlagSubsystem(hardwareMap, telemetry);
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

        /// comandos onciiados
        ChassisContainer.builder()
                .team(team)
                .target(limelight::getTx)
                .distance(limelight::getfrontal)
                .gamepad1(gamepad1)
                .gamepad2(gamepad2)
                .chassis(chassis)
                .build()
                .defineMainRoutine();


        GateContainer.builder()
                .subsystemGate(gate)
                .hasArtifact(outtake::hasArtifact)
                .gamepad(gamepad1)
                .timelaunch(triggerSubsystem::gatetime)
                .sensorSarcophagi(sarcophagi::getsensorSarcofogo)
                .sarcophagiMoment(sarcophagi::isSending)
                .isUnactive(sarcophagi::isUnactive)
                .build()
                .defineMainRoutine();

        IntakeContainer.builder()
                .gamepad(gamepad1)
                .hasArtifact(outtake::hasArtifact)
                .ArtifactComplete(outtake::noDebounceArtifacts)
                .subsytemIntake(intake)
                .isUnactive(sarcophagi::isUnactive)
                .intakeMoment(triggerSubsystem::intakeTimePower)
                .oneSent(outtake::oneSent)
                .sarcophagiMoment(sarcophagi::isSending)
                .distance(limelight::getfrontal)
                .velocityVerifier(outtake::atSetpoint)
                .build()
                .defineMainRoutine();


        OuttakeContainer.builder()
                .gamepad1(gamepad1)
                .gamepad2(gamepad2)
                .distance(limelight::getfrontal)
                .hasArtifact(outtake::hasArtifact)
                .subsystem(outtake)
                .atsetponitcahssi(chassis::atSetpoint)
                .sarcophagiMoment(sarcophagi::isSending)
                .isUnactive(sarcophagi::isUnactive)
                .build()
                .defineMainRoutine();


        PlatformContainer.builder()
                .gamepad(gamepad1)
                .subsystemCalibrator(platform)
                .distance(limelight::getfrontal)
                .sarcophagiMoment(sarcophagi::isSending)
                .hasArtifact(outtake::hasArtifact)
                .telemetry(telemetry)
                .build()
                .defineMainRoutine();


        SarcophagiContainer.builder()
                .subsystemSarcofogo(sarcophagi)
                .gamepad1(gamepad1)
                .gamepad2(gamepad2)
                .hasArtifact(outtake::hasArtifact)
                .ArtifactComplete(outtake::noDebounceArtifacts)
                .artifactMoment(triggerSubsystem::artifactMoment)
                .flagSubsystem(flagSubsystem)
                .build()
                .defineMainRoutine();

        TriggerContainer.builder()
                .hasArtifact(outtake::hasArtifact)
                .gamepad1(gamepad1)
                .gamepad2(gamepad2)
                .triggerSubsystem(triggerSubsystem)
                .velocityVerifier(outtake::atSetpoint)
                .limelightAcceptance(limelight::isValid)
                .translationalSetpoint(chassis::atSetpoint)
                .resetMemory(sarcophagi::resetmemore)
                .resetOuttake(outtake::resetTimeLaunch)
                .build()
                .defineMainRoutine();
    }
}
