package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.everest.CommandBased.compositions.ParallelCommandGroup;
import com.everest.CommandBased.compositions.RepeatCommand;
import com.everest.CommandBased.compositions.SequentialCommandGroup;
import com.everest.CommandBased.definition.Command;
import com.everest.CommandBased.essentials.Trigger;
import com.everest.CommandBased.util.ConditionalCommand;
import com.everest.CommandBased.util.InstantCommand;
import com.everest.CommandBased.util.WaitCommand;
import com.everest.constants.Constants;
import com.everest.constants.Pattern;
import com.everest.constants.meta.EnumTeam;
import com.everest.intake.Command.CommandIntake;
import com.everest.intake.Subsystem.SubsytemIntake;
import com.everest.outtake.command.AutoLime3A;
import com.everest.outtake.subsystem.SubsystemOuttake;
import com.everest.plataform.command.AutoLime3AC;
import com.everest.plataform.subsystem.SubsystemCalibrator;
import com.everest.trigger.command.TriggerCommand;
import com.everest.trigger.subsystem.TriggerSubsystem;
import com.example.chassi.command.AlignToAngle;
import com.example.chassi.MecanumDrive;
import com.example.chassi.roadrunner.command.RoadRunnerWrapper;
import com.example.gate.SubsystemGate;
import com.example.limelightcentral.Subsystem;
import com.example.sarcofogo.SubsystemSarcofogo;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class AutonomousRoutine {
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
    public AutonomousRoutine(
            HardwareMap hardwareMap,
            Telemetry telemetry,
            EnumTeam team
    ) {
        this.triggerSubsystem = new TriggerSubsystem(hardwareMap, telemetry);
        this.subsystemOuttake = new SubsystemOuttake(hardwareMap, telemetry);
        this.subsystemSarcofogo = new SubsystemSarcofogo(hardwareMap,telemetry);
        this.team = team;
        this.telemetry = telemetry;
        this.intake = new SubsytemIntake(hardwareMap, telemetry);
        this.subsystemCalibrator = new SubsystemCalibrator(hardwareMap, telemetry);
        this.subsystemGate = new SubsystemGate(hardwareMap, telemetry);
        this.chassi = new MecanumDrive(
                hardwareMap,
                telemetry,
                team


        );

        this.subLime = new Subsystem(hardwareMap, telemetry, team,chassi::getYaw );
        subsystemGate.setDefaultCommand(
                new com.example.gate.Command(subsystemGate,Constants.GateClosePosition)
        );
        new Trigger(subsystemOuttake::hasArtifact).whileFalse( new com.example.gate.Command(subsystemGate,0));

        intake.setDefaultCommand(new CommandIntake(intake, 0.65));

        obelisco().schedule();
        if (team.getPipeline() == 0)
            BLUELONGECOMPLETO();
        else
            REDLONGECOMPLETO();
    }


    TranslationalVelConstraint velConstraint(int velocity){
        return new TranslationalVelConstraint(velocity);
    }

    public void REDLONGECOMPLETO(){
        new SequentialCommandGroup(
//

                new InstantCommand(chassi::resetIMU),
                strafeToLinearHeading(-4,-8,-22,32),/// mira 1
                LancarAuto(),
                strafeToLinearHeading(10,-27.3,90,32),/// coleta 1
                strafeToLinearHeading(29,-27.3,90,25),
                strafeToLinearHeading(0,-8,-22,40),/// mira 2
                LancarAuto(),
                strafeToLinearHeading(5,-52,90,32),/// coleta 2
                strafeToLinearHeading(29,-52,90,25),
                strafeToLinearHeading(0,-8,-15,32),//// mira 3
                LancarAuto(),
                strafeToLinearHeading(10,-28.3,0,39)/// final



        ).schedule();
    }
    public void BLUELONGECOMPLETO(){
        new SequentialCommandGroup(

               new InstantCommand(chassi::resetIMU),
                strafeToLinearHeading(-4,-8,22,32),/// mira 1
                LancarAuto(),
                strafeToLinearHeading(-10,-28.3,-90,45),/// coleta 1
                strafeToLinearHeading(-29,-28.3,-90,15),
                strafeToLinearHeading(0,-8,22,45),/// mira 2
                LancarAuto(),
                strafeToLinearHeading(-5,-52,-90,45),/// coleta 2
                strafeToLinearHeading(-29,-52,-90,17),
                strafeToLinearHeading(0,-8,15,45),//// mira 3
                LancarAuto(),

                strafeToLinearHeading(-10,-28.3,0,45)/// final*/
        ).schedule();
    }
    public Command strafeToLinearHeading(double x, double y, double angulo,int velocity){
        return new RoadRunnerWrapper(chassi,
                c->c.actionBuilder(
                        chassi.localizer.getPose()).strafeToLinearHeading(
                        new Vector2d(y,x), Math.toRadians(angulo),
                        velConstraint(velocity)));
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
        return new AlignToAngle(telemetry, subLime::getTx, chassi,//chassi
                subLime::getfrontal,
                chassi.getPid(),team.getIncrement(),
                team.getShortIncrement(),team.getLargeIncrement()
        );

    }
    public Command atirar(){
        return new TriggerCommand(
                triggerSubsystem,
                Constants.targetLeftPosition,
                Constants.targetRightPosition,
                ()->{}
        ).ateQUe(()->!subsystemOuttake.hasArtifact()).
                antesDe(new ConditionalCommand(
                        ()->(
                                subsystemOuttake.atSetpoint())
                                &&(subsystemOuttake.hasArtifact()
                                &&(chassi.atSetpoint())))
                );
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

        ).ateQUe(triggerSubsystem::contlaunchtimes).espere(5,Constants.clockSeconds);

    }

    public void  Linetox(){
        new RoadRunnerWrapper(chassi,
                c->c.actionBuilder(chassi.localizer.getPose()).lineToX(2)).schedule();
    }
    public void  Strafeto(){
        new RoadRunnerWrapper(chassi,
                c->c.actionBuilder(chassi.localizer.getPose()).strafeTo(new Vector2d(-40, 0))).schedule();
    }
    public void  moverMANUAL(){

    }

}
