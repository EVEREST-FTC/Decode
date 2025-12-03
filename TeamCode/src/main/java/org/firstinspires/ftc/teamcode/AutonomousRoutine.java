package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Vector2d;
import com.everest.CommandBased.compositions.ParallelCommandGroup;
import com.everest.CommandBased.compositions.RepeatCommand;
import com.everest.CommandBased.compositions.SequentialCommandGroup;
import com.everest.CommandBased.definition.Command;
import com.everest.CommandBased.essentials.Trigger;
import com.everest.CommandBased.util.ConditionalCommand;
import com.everest.CommandBased.util.WaitCommand;
import com.everest.constants.Constants;
import com.everest.constants.EnumTeam;
import com.everest.intake.Command.CommandIntake;
import com.everest.intake.Subsystem.SubsytemIntake;
import com.everest.outtake.command.AutoLime3A;
import com.everest.outtake.subsystem.SubsystemOuttake;
import com.everest.plataform.command.AutoLime3AC;
import com.everest.plataform.subsystem.SubsystemCalibrator;
import com.everest.trigger.command.TriggerCommand;
import com.everest.trigger.subsystem.TriggerSubsystem;
import com.example.chassi.AlignToAngle;
import com.example.chassi.MecanumDrive;
import com.example.chassi.roadrunner.command.RoadRunnerWrapper;
import com.example.gate.SubsystemGate;
import com.example.limelightcentral.Subsystem;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class AutonomousRoutine {
    private final MecanumDrive chassi;
    private final TriggerSubsystem triggerSubsystem;
    private final SubsystemOuttake subsystemOuttake;
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
        this.subLime = new Subsystem(hardwareMap, telemetry, team);
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
        subsystemGate.setDefaultCommand(
                new com.example.gate.Command(subsystemGate,0)
        );
        intake.setDefaultCommand(new CommandIntake(intake, Constants.INTAKE_POWER));
        new Trigger(subsystemOuttake::hasArtifact).whileTrue( new com.example.gate.Command(subsystemGate,Constants.GateInitialPosition));
        REDLONGECOMPLETO();
    }

    public void REDLONGECOMPLETO(){
        new SequentialCommandGroup(
                strafeToLinearHeading(0,-5,-22),
                new WaitCommand(0.2,Constants.clockSeconds),
                Lancar(),
                strafeToLinearHeading(16.458,-30.2,104),
                strafeToLinearHeading(34,-30.2,104),
                strafeToLinearHeading(0,-5,-22),
                Lancar(),
                strafeToLinearHeading(16,-55,104),
                strafeToLinearHeading(34,-55,104)

        ).schedule();
    }
    public Command strafeToLinearHeading(double x, double y, double angulo){
        return new RoadRunnerWrapper(chassi,
                c->c.actionBuilder(
                        chassi.localizer.getPose()).strafeToLinearHeading(
                        new Vector2d(y,x), Math.toRadians(angulo)));
    }
    public Command Lancar(){

        return new ParallelCommandGroup(
                new RepeatCommand(//comando do trigger

                        new TriggerCommand(
                                triggerSubsystem,
                                Constants.targetLeftPosition,
                                Constants.targetRightPosition
                        ).ateQUe(()->!subsystemOuttake.hasArtifact()).
                                antesDe(new ConditionalCommand(
                                        ()->(
                                                subsystemOuttake.atSetpoint())
                                                &&(subsystemOuttake.hasArtifact()
                                                &&(chassi.atSetpoint())))
                                )),
                new AlignToAngle(subLime::getTx, chassi,//chassi
                () -> 0,
                () -> 0,
                        subLime::getfrontal,
                chassi.getPid(),team.getIncrement(),
                        team.getShortIncrement()
                ),
                new AutoLime3A(subLime::getfrontal,subsystemOuttake),//outtake
                new AutoLime3AC(subLime::getfrontal,subsystemCalibrator,telemetry)//plataforma,

        ).espere(5,Constants.clockSeconds) ;

    }

    public void  Linetox(){
        new RoadRunnerWrapper(chassi,
                c->c.actionBuilder(chassi.localizer.getPose()).lineToX(2)).schedule();
    }
    public void  Strafeto(){
        new RoadRunnerWrapper(chassi,
                c->c.actionBuilder(chassi.localizer.getPose()).strafeTo(new Vector2d(-40, 0))).schedule();
    }
}
