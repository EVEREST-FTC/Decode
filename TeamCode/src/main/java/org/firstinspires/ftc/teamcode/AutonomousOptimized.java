package org.firstinspires.ftc.teamcode;

import com.everest.CommandBased.compositions.ParallelCommandGroup;
import com.everest.CommandBased.compositions.RepeatCommand;
import com.everest.CommandBased.compositions.SequentialCommandGroup;
import com.everest.CommandBased.definition.Command;
import com.everest.CommandBased.essentials.Trigger;
import com.everest.CommandBased.util.InstantCommand;
import com.everest.CommandBased.util.WaitCommand;
import com.everest.constants.Constants;
import com.everest.constants.Pattern;
import com.everest.constants.meta.EnumTeam;
import com.everest.constants.meta.RobotContainer;
import com.everest.intake.Command.CommandIntake;
import com.everest.intake.Subsystem.SubsytemIntake;
import com.everest.outtake.command.AutoLime3A;
import com.everest.outtake.subsystem.SubsystemOuttake;
import com.everest.plataform.command.AutoLime3AC;
import com.everest.plataform.subsystem.SubsystemCalibrator;
import com.everest.trigger.subsystem.TriggerSubsystem;
import com.example.chassi.MecanumDrive;
import com.example.gate.SubsystemGate;
import com.example.limelightcentral.Subsystem;
import com.example.sarcofogo.SubsystemSarcofogo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import lombok.Builder;
/// Em testes: passando a responsabilidade dos comandos mais básicos para seus subssitemas, de modo que
/// possam ser reaproveitados em outros containers
@Builder
public class AutonomousOptimized implements RobotContainer {

    private final MecanumDrive chassi;
    private final TriggerSubsystem triggerSubsystem;
    private final SubsystemOuttake subsystemOuttake;
    private final SubsystemSarcofogo subsystemSarcofogo;
    private final Subsystem subLime;
    private final EnumTeam team;
    private final Telemetry telemetry;
    private final SubsytemIntake intake;
    private final SubsystemCalibrator subsystemCalibrator;
    private final SubsystemGate subsystemGate;

    public void REDLONGECOMPLETO(){

        new SequentialCommandGroup(
                new InstantCommand(chassi::resetIMU),
                chassi.strafeToLinearHeading(-4,-8,-22,32),/// mira 1
                new WaitCommand(0.3,Constants.clockSeconds),
                LancarAuto(),
                chassi.strafeToLinearHeading(10,-28.3,90,32),/// coleta 1
                chassi.strafeToLinearHeading(29,-28.3,90,18),
                chassi.strafeToLinearHeading(0,-8,-22,40),/// mira 2
                new WaitCommand(0.3,Constants.clockSeconds),
                LancarAuto(),
                chassi.strafeToLinearHeading(5,-52,90,32),/// coleta 2
                chassi.strafeToLinearHeading(29,-52,90,18),
                chassi.strafeToLinearHeading(0,-8,-15,32),//// mira 3
                new WaitCommand(0.3,Constants.clockSeconds),
                LancarAuto(),
                chassi.strafeToLinearHeading(10,-28.3,0,39)/// final
        ).schedule();
    }
    public void BLUELONGECOMPLETO(){
        new SequentialCommandGroup(
                new InstantCommand(chassi::resetIMU),
                chassi.strafeToLinearHeading(-4,-8,22,32),/// mira 1
                new WaitCommand(0.3,Constants.clockSeconds),
                LancarAuto(),
                chassi.strafeToLinearHeading(-10,-28.3,-90,45),/// coleta 1
                chassi.strafeToLinearHeading(-29,-28.3,-90,17),
                chassi.strafeToLinearHeading(0,-8,22,45),/// mira 2
                new WaitCommand(0.3,Constants.clockSeconds),
                LancarAuto(),
                chassi.strafeToLinearHeading(-5,-52,-90,45),/// coleta 2
                chassi.strafeToLinearHeading(-29,-52,-90,17),
                chassi.strafeToLinearHeading(0,-8,15,45),//// mira 3
                new WaitCommand(0.3,Constants.clockSeconds),
                LancarAuto(),
                chassi.strafeToLinearHeading(-10,-28.3,0,45)/// final
        ).schedule();
    }
    public void BLUEPERTOCOMPLETO(){

    }
    public void REDPERTOCOMPLETO(){

    }
    public Command obelisco(){
        return new SequentialCommandGroup(
                new InstantCommand(()->subLime.pipelineSwitch(2)),
                new InstantCommand(()->
                        Constants.matchPattern= Pattern.getById(subLime.getTagId())
                ),
                new InstantCommand(()->subLime.pipelineSwitch(team.getPipeline())));
    }

    public Command Mirar(){
        return chassi.mirar(team, subLime::getTx, subLime::getfrontal);

    }
    public Command atirar(){
        return triggerSubsystem.launch(subsystemOuttake::hasArtifact, subsystemOuttake::atSetpoint, chassi::atSetpoint);
    }

    public Command LancarAuto(){
        return new ParallelCommandGroup(
                        new SequentialCommandGroup(
                                new InstantCommand(triggerSubsystem::resettimelaunch),
                                new RepeatCommand(
                                        atirar()
                                )),
                Mirar(),
                new AutoLime3A(subLime::getfrontal,subsystemOuttake),//outtake
                new AutoLime3AC(subLime::getfrontal,subsystemCalibrator,telemetry)//plataforma,
        ).ateQUe(triggerSubsystem::contlaunchtimes).espere(7,Constants.clockSeconds);

    }
    @Override
    public void mainRoutine() {
        subsystemGate.setDefaultCommand(
                new com.example.gate.Command(subsystemGate,Constants.GateClosePosition)
        );
        new Trigger(subsystemOuttake::hasArtifact).whileFalse( new com.example.gate.Command(subsystemGate,0));

        intake.setDefaultCommand(new CommandIntake(intake, Constants.INTAKE_POWER));

        if (team == EnumTeam.SOLO_BLUE_FAR)
            BLUELONGECOMPLETO();
        else if (team == EnumTeam.SOLO_RED_FAR)
            REDLONGECOMPLETO();
        else if (team == EnumTeam.SOLO_RED_CLOSE)
            REDPERTOCOMPLETO();
        else
            BLUEPERTOCOMPLETO();

        obelisco(); ///  identificar obelisco
    }
}
