package org.firstinspires.ftc.teamcode;

import static com.everest.constants.Constants.ControllerConstants.GAMEPAD_AIM_TRIGGER;
import static com.everest.constants.Constants.GateConstants.GateClosePosition;
import static com.everest.constants.Constants.GateConstants.GateOpenPosition;
import static com.everest.constants.Constants.IntakeConstants.INTAKE_POWER;
import static com.everest.constants.Constants.IntakeConstants.INTAKE_POWER_CLOSE;
import static com.everest.constants.Constants.SarcofagoConstants.SarcofogoInitialPosition;

import com.everest.CommandBased.compositions.ParallelCommandGroup;
import com.everest.CommandBased.compositions.ParallelRaceGroup;
import com.everest.CommandBased.compositions.RepeatCommand;
import com.everest.CommandBased.compositions.SelectCommand;
import com.everest.CommandBased.compositions.SequentialCommandGroup;
import com.everest.CommandBased.definition.Command;
import com.everest.CommandBased.essentials.Trigger;
import com.everest.CommandBased.util.ConditionalCommand;
import com.everest.CommandBased.util.InstantCommand;
import com.everest.CommandBased.util.WaitCommand;
import com.everest.constants.Constants;
import com.everest.constants.Pattern;
import com.everest.constants.meta.EnumTeam;
import com.everest.constants.meta.RobotContainer;
import com.everest.intake.Command.CommandIntake;
import com.everest.intake.Subsystem.SubsytemIntake;
import com.everest.outtake.command.AutoLime3A;
import com.everest.outtake.command.LaunchCommand;
import com.everest.outtake.subsystem.SubsystemOuttake;
import com.everest.plataform.command.AutoLime3AC;
import com.everest.plataform.subsystem.SubsystemCalibrator;
import com.everest.trigger.subsystem.TriggerSubsystem;
import com.example.chassi.MecanumDrive;
import com.example.gate.State;
import com.example.gate.SubsystemGate;
import com.example.limelightcentral.Subsystem;
import com.example.sarcofogo.CommandBandeira;
import com.example.sarcofogo.Moment;
import com.example.sarcofogo.SubsystemSarcofogo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.Map;
import java.util.function.BooleanSupplier;

import lombok.Builder;
/// Em testes: passando a responsabilidade dos comandos mais básicos para seus subssitemas, de modo que
/// possam ser reaproveitados em outros containers
@Builder
public class AutonomousOptimized implements RobotContainer {

    protected final MecanumDrive chassis;
    protected final TriggerSubsystem triggerSubsystem;
    protected final SubsystemOuttake subsystemOuttake;
    protected final SubsystemSarcofogo subsystemSarcofogo;
    protected final Subsystem subLime;
    protected final EnumTeam team;
    protected final Telemetry telemetry;
    protected final SubsytemIntake intake;
    protected final SubsystemCalibrator subsystemCalibrator;
    protected final SubsystemGate subsystemGate;
    protected boolean isAiming;
    protected boolean isSending;

    public void farRedComplete(){

        new SequentialCommandGroup(
                obelisk(),
                new InstantCommand(chassis::resetIMU),
                chassis.strafeToLinearHeading(-4,-8,-22,50),/// mira 1

                firstLaunch(),
                chassis.strafeToLinearHeading(10,-28.3,90,50),/// coleta 1
                chassis.strafeToLinearHeading(29,-28.3,90,15),

                chassis.strafeToLinearHeading(0,-8,-22,50),///mira 2
                counting(),
                autoLaunch(),

                chassis.strafeToLinearHeading(5,-52,90,50),/// coleta 2
                chassis.strafeToLinearHeading(29,-52,90,15),

                chassis.strafeToLinearHeading(0,-8,-22,50),//// mira 3
                counting(),
                autoLaunch(),
                chassis.strafeToLinearHeading(10,-28.3,0,50)/// final*/
        ).schedule();
    }
    public void farBlueComplete(){
        new SequentialCommandGroup(
                obelisk(),
                new InstantCommand(chassis::resetIMU),
                chassis.strafeToLinearHeading(4,-8,22,32),/// mira 1

                firstLaunch(),
                chassis.strafeToLinearHeading(-10,-28.3,-90,35),/// coleta 1
                chassis.strafeToLinearHeading(-46,-28.3,-90,30),
                chassis.strafeToLinearHeading(0,-8,22,45),/// mira 2


                counting(),
                autoLaunch(),
                chassis.strafeToLinearHeading(-5,-52,-90,35),/// coleta 2
                chassis.strafeToLinearHeading(-47,-52,-90,26),
                chassis.strafeToLinearHeading(0,-8,22,45),//// mira 3

                counting(),
                autoLaunch(),
                chassis.strafeToLinearHeading(-10,-28.3,0,45)/// final
        ).schedule();
    }
    public void closeBlueComplete(){
        new SequentialCommandGroup(
                new InstantCommand(()-> subLime.pipelineSwitch(2)),
                new InstantCommand(chassis::resetIMU),
                new ParallelRaceGroup(
                        chassis.strafeToLinearHeading(-10,30,20,32),
                        new Command() {
                            @Override
                            public void execute() {
                                Constants.setMatchPattern(Pattern.getById(subLime.getTagId()));
                            }

                            @Override
                            public void end(boolean interrupted) {
                                subLime.pipelineSwitch(team.getPipeline());
                            }
                        }
                ),
                chassis.strafeToLinearHeading(10,30,-50,32),/// mira 1
                firstLaunch(),
                chassis.strafeToLinearHeading(7,50,90,32),/// coleta 1
                chassis.strafeToLinearHeading(-20,50,90,26),

                chassis.strafeToLinearHeading(10,30,-50,32),/// mira 2
                counting(),
                autoLaunch(),

                chassis.strafeToLinearHeading(7,75,90,32),/// coleta 2
                chassis.strafeToLinearHeading(-25,75,90,26),

                chassis.strafeToLinearHeading(10,30,-50,32),/// mira 3
                counting(),
                autoLaunch(),

                chassis.strafeToLinearHeading(-11,60,0,32)/// final




        ).schedule();
    }
    public void closeRedComplete(){
        new SequentialCommandGroup(
                new InstantCommand(()-> subLime.pipelineSwitch(2)),
                new InstantCommand(chassis::resetIMU),
                new ParallelRaceGroup(
                        chassis.strafeToLinearHeading(-10,30,20,32),
                        new Command() {
                                    @Override
                                    public void execute() {
                                        Constants.setMatchPattern(Pattern.getById(subLime.getTagId()));
                                    }

                                    @Override
                                    public void end(boolean interrupted) {
                                        subLime.pipelineSwitch(team.getPipeline());
                                    }
                        }
                ),
                chassis.strafeToLinearHeading(-10,30,-50,32),/// mira 1
                firstLaunch(),
                chassis.strafeToLinearHeading(-7,50,90,32),/// coleta 1
                chassis.strafeToLinearHeading(20,50,90,26),

                chassis.strafeToLinearHeading(-10,30,-50,32),/// mira 2
                counting(),
                autoLaunch(),

                chassis.strafeToLinearHeading(-7,75,90,32),/// coleta 2
                chassis.strafeToLinearHeading(25,75,90,26),

                chassis.strafeToLinearHeading(-10,30,-50,32),/// mira 3
                counting(),
                autoLaunch(),

                chassis.strafeToLinearHeading(11,60,0,32)/// final




        ).schedule();


    }


    public Command obelisk(){
        return new SequentialCommandGroup(
                new InstantCommand(()->subLime.pipelineSwitch(2)),
                new InstantCommand(()->
                        Constants.setMatchPattern(Pattern.BOTTOM)
                        //Constants.setMatchPattern(Pattern.getById(subLime.getTagId()))
                ),
                new InstantCommand(()->subLime.pipelineSwitch(team.getPipeline())));
    }

    public Command aim(){
        return chassis.mirar(team, subLime::getTx, subLime::getfrontal);

    }
    public Command launch(){
        return triggerSubsystem.launch(subsystemOuttake::hasArtifact, subsystemOuttake::atSetpoint);
    }

    public Command firstLaunch(){

        return new ParallelCommandGroup(
                new SequentialCommandGroup(
                        new InstantCommand(triggerSubsystem::resetTimeLaunch),
                        new RepeatCommand(
                                launch()
                        )
                ),
                new AutoLime3AC(subLime::getfrontal,subsystemCalibrator,telemetry)//plataforma,
            ).antesDe(new InstantCommand(()->{
                    isAiming=true;
                    triggerSubsystem.setLastTarget(3);
                    triggerSubsystem.resetTimeLaunch();
                    isSending = true;
                }))
                .ateQUe(triggerSubsystem::contLaunchTimes)
                .depois(new InstantCommand(()->{
                    isAiming = false;
                    isSending = false;
                }));

    }
    public Command counting(){
        return new SequentialCommandGroup(
                new WaitCommand(0.4, Constants.robotTimer),
                new InstantCommand(()->triggerSubsystem.setLastTarget(subsystemOuttake.artifacts())));
    }
    public Command autoLaunch(){

        return new ParallelCommandGroup(
                new RepeatCommand(
                        triggerSubsystem.launch(subsystemSarcofogo::resetmemore)
                                .ateQUe(()->!subsystemOuttake.hasArtifact()).
                                antesDe(conditionalCommand())
                ),
                new AutoLime3AC(subLime::getfrontal,subsystemCalibrator,telemetry)//plataforma,
        )
                .antesDe(new InstantCommand(()->isAiming=true))
                .ateQUe(triggerSubsystem::contLaunchTimes)
                .espere(7,Constants.robotTimer)
                .antesDe( new InstantCommand(triggerSubsystem::resetTimeLaunch))
                .depois(new InstantCommand(()->isAiming=false));

    }
    @Override
    public void mainRoutine() {
        outtakeRoutine();
        gateRoutine();
        intakeRoutine();
        sarcophagiRoutine();
        if (team == EnumTeam.SOLO_BLUE_FAR)
            farBlueComplete();
        else if (team == EnumTeam.SOLO_RED_FAR)
            farRedComplete();
        else if (team == EnumTeam.SOLO_RED_CLOSE)
            closeRedComplete();
        else
            closeBlueComplete();


    }


    protected void gateRoutine(){

        subsystemGate.setDefaultCommand(
                new SelectCommand<>(
                        Map.ofEntries(
                                Map.entry(State.CLOSED, new com.example.gate.Command(subsystemGate, GateClosePosition)),
                                Map.entry(State.OPENED, new com.example.gate.Command(subsystemGate,GateOpenPosition)),
                                Map.entry(State.BOTTOM_SELECTION, new com.example.gate.Command(subsystemGate, GateClosePosition))
                        ),
                        ()->State.selector(subsystemOuttake.hasArtifact(),
                                isSending,
                                subsystemSarcofogo.isUnactive())
                )
        );
        new Trigger(subsystemSarcofogo::isSending).whileTrue(
                new com.example.gate.Command(subsystemGate, GateClosePosition)
        );
    }
    protected void outtakeRoutine(){
        subsystemOuttake.setDefaultCommand(
                new AutoLime3A(subLime::getfrontal, subsystemOuttake).ateQUe(()->
                        (!isAiming&&
                                !isSending)||
                                (Constants.getMatchPattern().equals(Pattern.BOTTOM)&&
                                        subsystemSarcofogo.isSending()&&
                                        !subsystemOuttake.hasArtifact())));

        new Trigger(subsystemOuttake::hasArtifact).whileFalse(new LaunchCommand(subsystemOuttake, -0.2));
    }
    protected void intakeRoutine(){


       intake.setDefaultCommand(new CommandIntake(intake, (team==EnumTeam.SOLO_BLUE_FAR||team==EnumTeam.SOLO_RED_FAR)?
               0.85:
               INTAKE_POWER_CLOSE
               ));
        new Trigger(subsystemOuttake::hasArtifact).and(()->isAiming).whileTrue(new CommandIntake(intake, 0.25));

    }
    protected void sarcophagiRoutine(){
        subsystemSarcofogo.setDefaultCommand(
                new SelectCommand<>(
                        Map.ofEntries(
                                Map.entry(Moment.KEEP, new com.example.sarcofogo.Command(subsystemSarcofogo,
                                        SarcofogoInitialPosition, Moment.KEEP)),
                                Map.entry(Moment.SEND, new com.example.sarcofogo.Command(subsystemSarcofogo,50, Moment.SEND).ateQUe(()->!triggerSubsystem.artifactMoment()))
                        ),
                        ()->Moment.select(triggerSubsystem.artifactMoment()&&isSending)
                ));
        new Trigger(()->!isSending).toggleOnTrue(new com.example.sarcofogo.Command(subsystemSarcofogo,0, Moment.UNACTIVE));

        new Trigger(()->isSending).onFalse(new InstantCommand(subsystemSarcofogo::resetmemore));
    }
    protected Command conditionalCommand() {
        BooleanSupplier triggerCondition = () ->
                subLime.isValid()
                        && subsystemOuttake.atSetpoint()
                        && subsystemOuttake.hasArtifact();
        return new ConditionalCommand(triggerCondition);
    }
}
