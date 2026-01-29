package org.firstinspires.ftc.teamcode;

import static com.everest.constants.Constants.CameraConstants.shortIncrementDistance;
import static com.everest.constants.Constants.GateConstants.GateClosePosition;
import static com.everest.constants.Constants.GateConstants.GateOpenPosition;
import static com.everest.constants.Constants.GyroConstants.KD;
import static com.everest.constants.Constants.GyroConstants.KI;
import static com.everest.constants.Constants.GyroConstants.KP;
import static com.everest.constants.Constants.IntakeConstants.INTAKE_POWER;
import static com.everest.constants.Constants.IntakeConstants.INTAKE_POWER_CLOSE;
import static com.everest.constants.Constants.PlatformConstants.CLOSE_POWER_LAUNCHER_CONVERSION;
import static com.everest.constants.Constants.PlatformConstants.FAR_POWER_LAUNCHER_CONVERSION;
import static com.everest.constants.Constants.PlatformConstants.POWER_LAUNCHER_CONVERSION;
import static com.everest.constants.Constants.SarcofagoConstants.SarcofogoInitialPosition;

import com.everest.CommandBased.compositions.ParallelCommandGroup;
import com.everest.CommandBased.compositions.RepeatCommand;
import com.everest.CommandBased.compositions.SelectCommand;
import com.everest.CommandBased.compositions.SequentialCommandGroup;
import com.everest.CommandBased.definition.Command;
import com.everest.CommandBased.definition.CommandScheduler;
import com.everest.CommandBased.essentials.Trigger;
import com.everest.CommandBased.util.ConditionalCommand;
import com.everest.CommandBased.util.InstantCommand;
import com.everest.CommandBased.util.WaitCommand;
import com.everest.constants.ClockAdapter;
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
import com.example.sarcofogo.FlagSubsystem;
import com.example.sarcofogo.Moment;
import com.example.sarcofogo.SubsystemSarcofogo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.BooleanSupplier;

/// Em testes: passando a responsabilidade dos comandos mais básicos para seus subssitemas, de modo que
/// possam ser reaproveitados em outros containers
public abstract class AutonomousOptimized extends LinearOpMode
        implements RobotContainer {
    protected EnumTeam team;


    protected abstract void route();
    protected abstract EnumTeam getTeam();

    @Override
    public void mainRoutine() {
        initSubsystems();
        outtakeRoutine();
        flagRoutine();
        gateRoutine();
        intakeRoutine();
        sarcophagiRoutine();
        route();
    }

    @Override
    public void runOpMode(){
        team = getTeam();
        CommandScheduler.getInstance().reset();
        waitForStart();

        mainRoutine();

        while (opModeIsActive()) {
            CommandScheduler.getInstance().run();
            telemetry.addData("Comandos", CommandScheduler.getInstance().m_scheduledCommands.size());
            telemetry.update();
        }
        //limpa o singleton no requerimento de stop
        CommandScheduler.getInstance().reset();
    }

    protected MecanumDrive chassis;
    protected TriggerSubsystem triggerSubsystem;
    protected SubsystemOuttake subsystemOuttake;
    protected SubsystemSarcofogo subsystemSarcofogo;
    protected Subsystem subLime;
    protected SubsytemIntake intake;
    protected SubsystemCalibrator subsystemCalibrator;
    protected SubsystemGate subsystemGate;
    protected FlagSubsystem flagSubsystem;
    private boolean isAiming;
    private boolean isSending;
    private double intakeAddPower;
    private boolean intakeTime;
    private void initSubsystems(){
        chassis = new MecanumDrive(hardwareMap,
                telemetry,
                team,
                KP,
                KI,
                KD);
        subsystemGate = new SubsystemGate(hardwareMap,
                telemetry);
        subsystemSarcofogo = new SubsystemSarcofogo(hardwareMap,
                telemetry);
        subsystemOuttake = new SubsystemOuttake(hardwareMap,
                telemetry);
        subsystemCalibrator =new SubsystemCalibrator(hardwareMap,
                telemetry);
        triggerSubsystem = new TriggerSubsystem(hardwareMap,
                telemetry);
        intake = new SubsytemIntake(hardwareMap,
                telemetry);
        subLime = new Subsystem(hardwareMap,
                telemetry,
                team,
                chassis::getYaw
        );
        flagSubsystem= new FlagSubsystem(hardwareMap,telemetry);
    }


    public Command obelisk(){
        return new SequentialCommandGroup(
                new InstantCommand(()->subLime.pipelineSwitch(2)),
                new InstantCommand(()->
                        /*Constants.setMatchPattern(Pattern.BOTTOM)*/
                        Constants.setMatchPattern(Pattern.getById(subLime.getTagId()))
                ),
                new InstantCommand(()->subLime.pipelineSwitch(team.getPipeline())));
    }

    public Command firstLaunch(){

        return new ParallelCommandGroup(
                new SequentialCommandGroup(
                        new WaitCommand(3.5, Constants.robotTimer),
                        new InstantCommand(()->intakeTime = true)
                ),
                aim(),
                new RepeatCommand(
                        triggerSubsystem.launch(subsystemSarcofogo::resetmemore)
                                .ateQUe(()->!subsystemOuttake.hasArtifact()).
                                antesDe(conditionalCommand())
                ),
                new AutoLime3AC(subLime::getfrontal,subsystemCalibrator,telemetry)
            ).antesDe(new InstantCommand(()->   {
                    isAiming=true;
                    triggerSubsystem.setLastTarget(3);
                    triggerSubsystem.resetTimeLaunch();
                    isSending = true;
                    intakeAddPower = 0;
                })).espere(8,Constants.robotTimer)
                .ateQUe(triggerSubsystem::contLaunchTimes)
                .depois(new InstantCommand(()->{
                    isAiming = false;
                    isSending = false;
                    intakeAddPower = 0;
                    intakeTime = false;
                }));

    }
    public Command counting(){
        return new SequentialCommandGroup(
                new WaitCommand(0.5, Constants.robotTimer),
                new RepeatCommand(
                        new InstantCommand(()->triggerSubsystem.setLastTarget(3) /*()->triggerSubsystem.setLastTarget(subsystemOuttake.artifacts())*/)
                ),
                new WaitCommand(5, Constants.robotTimer)
        );
    }
    public Command autoLaunch(){

        return new ParallelCommandGroup(
                aim(),
                new RepeatCommand(
                        triggerSubsystem.launch(subsystemSarcofogo::resetmemore)
                                .ateQUe(()->!subsystemOuttake.hasArtifact()).
                                antesDe(conditionalCommand())
                ),
                new AutoLime3AC(subLime::getfrontal,subsystemCalibrator,telemetry),
                new SequentialCommandGroup(
                        new WaitCommand(4, Constants.robotTimer),
                        new InstantCommand(()->intakeTime = true)
                )
        )
                .antesDe(new InstantCommand(()->isAiming=true))
                .ateQUe(triggerSubsystem::contLaunchTimes)
                .espere(9,Constants.robotTimer)
                .antesDe( new InstantCommand(triggerSubsystem::resetTimeLaunch))
                .depois(new InstantCommand(()->isAiming=false))
                .depois(new InstantCommand(()->intakeTime=false))
                .depois(new InstantCommand(()->triggerSubsystem.resetPosition()));

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
                new AutoLime3A(subLime::getfrontal, subsystemOuttake, FAR_POWER_LAUNCHER_CONVERSION, CLOSE_POWER_LAUNCHER_CONVERSION, POWER_LAUNCHER_CONVERSION,chassis.atSetpoint()).ateQUe(()->
                        (!isAiming&&
                                !isSending)||
                                (Constants.getMatchPattern().equals(Pattern.BOTTOM)&&
                                        subsystemSarcofogo.isSending()&&
                                        !subsystemOuttake.hasArtifact())));
        new Trigger(()->!subsystemOuttake.hasArtifact()).and(subsystemSarcofogo::isSending).whileTrue(new LaunchCommand(subsystemOuttake, -0.1));
    }

    protected void flagRoutine(){
        new Trigger(()->subsystemOuttake.artifacts()==3).whileTrue(
                new CommandBandeira(flagSubsystem,90)
        );
    }


    protected void intakeRoutine(){


       intake.setDefaultCommand(new CommandIntake(intake, (team==EnumTeam.SOLO_BLUE_FAR||team==EnumTeam.SOLO_RED_FAR)?
               INTAKE_POWER + intakeAddPower :
              INTAKE_POWER_CLOSE + intakeAddPower
               ));
        new Trigger(subsystemOuttake::hasArtifact).and(()->isAiming).or(()->subsystemOuttake.artifacts()==3)
                .whileTrue(new CommandIntake(intake, 0));

        new Trigger(()->intakeTime || triggerSubsystem.intakeTimePower())
                .and(()->isAiming)
                .and(()->!subsystemOuttake.hasArtifact())
                .and(()->subLime.getfrontal()>shortIncrementDistance)
                .whileTrue(
                        new CommandIntake(intake, 0.04));
    }
    protected void sarcophagiRoutine(){
        subsystemSarcofogo.setDefaultCommand(
                new SelectCommand<>(
                        Map.ofEntries(
                                Map.entry(Moment.KEEP, new com.example.sarcofogo.Command(subsystemSarcofogo,
                                        SarcofogoInitialPosition, Moment.KEEP)),
                                Map.entry(Moment.SEND, new com.example.sarcofogo.Command(subsystemSarcofogo,100 , Moment.SEND).ateQUe(()->!triggerSubsystem.artifactMoment()))
                        ),
                        ()->Moment.select(triggerSubsystem.artifactMoment()&&isSending)
                ));
        new Trigger(()->!isSending).whileTrue(new com.example.sarcofogo.Command(subsystemSarcofogo,0, Moment.UNACTIVE));

        new Trigger(()->isSending).onFalse(new InstantCommand(subsystemSarcofogo::resetmemore));
    }
    protected Command conditionalCommand() {
        BooleanSupplier triggerCondition = () ->
                subLime.isValid()
                        && subsystemOuttake.atSetpoint()
                        && subsystemOuttake.hasArtifact()
                ;
        return new ConditionalCommand(triggerCondition);
    }
    protected Command aim(){
        return chassis.mirar(team, subLime::getTx, subLime::getfrontal);
    }
}
