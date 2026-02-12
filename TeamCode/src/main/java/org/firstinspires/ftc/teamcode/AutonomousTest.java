package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.everest.CommandBased.compositions.RepeatCommand;
import com.everest.CommandBased.compositions.SequentialCommandGroup;
import com.everest.CommandBased.definition.Command;
import com.everest.CommandBased.util.InstantCommand;
import com.everest.constants.meta.EnumTeam;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

import java.util.Optional;

@Autonomous
public class AutonomousTest extends AutonomousDefinitions{
    @Override
    protected void route() {
        new SequentialCommandGroup(
                new InstantCommand(()->subLime.pipelineSwitch(2)),
                new InstantCommand(()-> {
                    Optional<Pose3D> pose = subLime.getBotPoseTargetPerspective();
                    if(!pose.isPresent()) return;
                    chassis.setPose(
                            new Pose2d(
                                    pose.get().getPosition().toUnit(DistanceUnit.INCH).x,
                                    pose.get().getPosition().toUnit(DistanceUnit.INCH).y,
                                    chassis.getYaw()));
                })
        ).schedule();
    }

    @Override
    protected EnumTeam getTeam() {
        return EnumTeam.SOLO_RED_FAR;
    }

    @Override
    protected void structurePaths() {

    }
}
