package org.firstinspires.ftc.teamcode;

import static com.everest.constants.Constants.GateConstants.GateClosePosition;
import static com.everest.constants.Constants.IntakeConstants.INTAKE_POWER;
import static com.everest.constants.Constants.IntakeConstants.INTAKE_POWER_CLOSE;

import com.everest.CommandBased.compositions.ParallelCommandGroup;
import com.everest.CommandBased.compositions.ParallelRaceGroup;
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
    private boolean isAiming = false;

    public void REDLONGECOMPLETO(){

        new SequentialCommandGroup(
                obelisco(),
                new InstantCommand(chassi::resetIMU),
                chassi.strafeToLinearHeading(-4,-8,-20,32),/// mira 1
                LancarPrimeiro(),
                chassi.strafeToLinearHeading(10,-28.3,90,35),/// coleta 1
                chassi.strafeToLinearHeading(43,-28.3,90,30),
                chassi.strafeToLinearHeading(0,-8,-22,45),/// mira 2
                LancarAuto(),
                chassi.strafeToLinearHeading(5,-51,90,35),/// coleta 2
                chassi.strafeToLinearHeading(47,-52,90,24),
                chassi.strafeToLinearHeading(0,-8,-15,45),//// mira 3
                LancarAuto(),
                chassi.strafeToLinearHeading(10,-28.3,0,39)/// final
        ).schedule();
    }
    public void BLUELONGECOMPLETO(){
        new SequentialCommandGroup(
                obelisco(),
                new InstantCommand(chassi::resetIMU),
                chassi.strafeToLinearHeading(4,-8,22,32),/// mira 1

                LancarPrimeiro(),
                chassi.strafeToLinearHeading(-10,-28.3,-90,35),/// coleta 1
                chassi.strafeToLinearHeading(-46,-28.3,-90,30),
                chassi.strafeToLinearHeading(0,-8,22,45),/// mira 2


                LancarAuto(),
                chassi.strafeToLinearHeading(-5,-52,-90,35),/// coleta 2
                chassi.strafeToLinearHeading(-47,-52,-90,26),
                chassi.strafeToLinearHeading(0,-8,15,45),//// mira 3

                LancarAuto(),
                chassi.strafeToLinearHeading(-10,-28.3,0,45)/// final
        ).schedule();
    }
    public void BLUEPERTOCOMPLETO(){
        new SequentialCommandGroup(
                new InstantCommand(()-> subLime.pipelineSwitch(2)),
                new InstantCommand(chassi::resetIMU),
                new ParallelRaceGroup(
                        chassi.strafeToLinearHeading(-10,30,20,32),
                        new Command() {
                            @Override
                            public void execute() {
                                Constants.matchPattern= Pattern.getById(subLime.getTagId());
                            }

                            @Override
                            public void end(boolean interrupted) {
                                subLime.pipelineSwitch(team.getPipeline());
                            }
                        }
                ),
                chassi.strafeToLinearHeading(10,30,-50,32),/// mira 1
                LancarPrimeiro(),
                chassi.strafeToLinearHeading(7,50,90,32),/// coleta 1
                chassi.strafeToLinearHeading(-20,50,90,26),

                chassi.strafeToLinearHeading(10,30,-50,32),/// mira 2
                LancarAuto(),

                chassi.strafeToLinearHeading(7,75,90,32),/// coleta 2
                chassi.strafeToLinearHeading(-25,75,90,26),

                chassi.strafeToLinearHeading(10,30,-50,32),/// mira 3
                LancarAuto(),

                chassi.strafeToLinearHeading(-11,60,0,32)/// final




        ).schedule();
    }
    public void REDPERTOCOMPLETO(){
        new SequentialCommandGroup(
                new InstantCommand(()-> subLime.pipelineSwitch(2)),
                new InstantCommand(chassi::resetIMU),
                new ParallelRaceGroup(
                        chassi.strafeToLinearHeading(-10,30,20,32),
                        new Command() {
                                    @Override
                                    public void execute() {
                                        Constants.matchPattern= Pattern.getById(subLime.getTagId());
                                    }

                                    @Override
                                    public void end(boolean interrupted) {
                                        subLime.pipelineSwitch(team.getPipeline());
                                    }
                        }
                ),
                chassi.strafeToLinearHeading(-10,30,-50,32),/// mira 1
                LancarPrimeiro(),
                chassi.strafeToLinearHeading(-7,50,90,32),/// coleta 1
                chassi.strafeToLinearHeading(20,50,90,26),

                chassi.strafeToLinearHeading(-10,30,-50,32),/// mira 2
                LancarAuto(),

                chassi.strafeToLinearHeading(-7,75,90,32),/// coleta 2
                chassi.strafeToLinearHeading(25,75,90,26),

                chassi.strafeToLinearHeading(-10,30,-50,32),/// mira 3
                LancarAuto(),

                chassi.strafeToLinearHeading(11,60,0,32)/// final




        ).schedule();


    }
    public Command obelisco(){
        return new SequentialCommandGroup(
                new InstantCommand(()->
                        Constants.matchPattern= Pattern.getById(subLime.getTagId())
                ),
                new InstantCommand(()->subLime.pipelineSwitch(team.getPipeline())));
    }

    public Command Mirar(){
        return chassi.mirar(team, subLime::getTx, subLime::getfrontal);

    }
    public Command atirar(){
        return triggerSubsystem.launch(subsystemOuttake::hasArtifact, subsystemOuttake::atSetpoint)
                .antesDe(new InstantCommand(subsystemOuttake::resetmemore));
    }

    public Command LancarPrimeiro(){

        return new ParallelCommandGroup(
                new SequentialCommandGroup(
                        new InstantCommand(triggerSubsystem::resettimelaunch),
                        new RepeatCommand(
                                atirar()
                        )
                ),
                Mirar(),
                new AutoLime3A(subLime::getfrontal,subsystemOuttake).ateQUe((triggerSubsystem::contlaunchtimes)),//outtake
                new AutoLime3AC(subLime::getfrontal,subsystemCalibrator,telemetry)//plataforma,
        )
                .antesDe(new InstantCommand(()->isAiming=true))
                .antesDe(new InstantCommand(()->triggerSubsystem.setLastTarget(3)))
                .antesDe(new InstantCommand(triggerSubsystem::resettimelaunch))
                .ateQUe(triggerSubsystem::contlaunchtimes)
                .depois(new InstantCommand(()->isAiming=false));

    }
    public Command LancarAuto(){

        return new ParallelCommandGroup(
                        new SequentialCommandGroup(
                                new InstantCommand(triggerSubsystem::resettimelaunch),
                                new RepeatCommand(
                                        atirar()
                                )
                        ),
                Mirar(),
                new AutoLime3A(subLime::getfrontal,subsystemOuttake).ateQUe((triggerSubsystem::contlaunchtimes)),//outtake
                new AutoLime3AC(subLime::getfrontal,subsystemCalibrator,telemetry)//plataforma,
        )
                .antesDe(new InstantCommand(()->isAiming=true))
                .antesDe(new InstantCommand(()->triggerSubsystem.setLastTarget(subsystemOuttake.artifacts())))
                .ateQUe(triggerSubsystem::contlaunchtimes)
                .antesDe( new InstantCommand(triggerSubsystem::resettimelaunch))
                .depois(new InstantCommand(()->isAiming=false));

    }
    @Override
    public void mainRoutine() {
        subsystemGate.setDefaultCommand(
                new com.example.gate.Command(subsystemGate,GateClosePosition)
        );
        new Trigger(subsystemOuttake::hasArtifact).whileFalse( new com.example.gate.Command(subsystemGate,0));


        new InstantCommand(()->subLime.pipelineSwitch(2));
        intakeRoutine();

        if (team == EnumTeam.SOLO_BLUE_FAR)
            BLUELONGECOMPLETO();
        else if (team == EnumTeam.SOLO_RED_FAR)
            REDLONGECOMPLETO();
        else if (team == EnumTeam.SOLO_RED_CLOSE)
            REDPERTOCOMPLETO();
        else
            BLUEPERTOCOMPLETO();


    }


    private void intakeRoutine(){

       intake.setDefaultCommand(new CommandIntake(intake, (team==EnumTeam.SOLO_BLUE_FAR||team==EnumTeam.SOLO_RED_FAR)?
               INTAKE_POWER:
               INTAKE_POWER_CLOSE
               ));
        new Trigger(subsystemOuttake::hasArtifact).and(()->isAiming).whileTrue(new CommandIntake(intake, 0.25));

    }
}
