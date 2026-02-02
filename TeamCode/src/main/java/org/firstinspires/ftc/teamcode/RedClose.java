package org.firstinspires.ftc.teamcode;

import com.everest.CommandBased.compositions.ParallelCommandGroup;
import com.everest.CommandBased.compositions.RepeatCommand;
import com.everest.CommandBased.compositions.SequentialCommandGroup;
import com.everest.CommandBased.util.InstantCommand;
import com.everest.constants.meta.EnumTeam;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Disabled
@Autonomous(name = "REDPERTO")
public class RedClose extends AutonomousDefinitions {
    @Override
    protected void route() {
        new ParallelCommandGroup(
                new SequentialCommandGroup(
                        new InstantCommand(()-> subLime.pipelineSwitch(2)),
                        new InstantCommand(chassis::resetIMU),
                        chassis.strafeToLinearHeading(-10,30,20,15),
                        obelisk(),

                        chassis.strafeToLinearHeading(-10,30,-50,30),/// mira 1
                        // firstLaunch(),
                        chassis.strafeToLinearHeading(-7,50,90,32),/// coleta 1
                        chassis.strafeToLinearHeading(20,50,90,26),

                        chassis.strafeToLinearHeading(-10,30,-50,32),/// mira 2

                        //   autoLaunch(),

                        chassis.strafeToLinearHeading(-7,75,90,32),/// coleta 2
                        chassis.strafeToLinearHeading(25,75,90,26),

                        chassis.strafeToLinearHeading(-10,30,-50,32),/// mira 3

                        // autoLaunch(),

                        chassis.strafeToLinearHeading(11,60,0,32)/// final
                ),
                new RepeatCommand(new InstantCommand(subsystemOuttake::artifacts))
        ).schedule();

    }

    @Override
    protected void structurePaths() {}

    @Override
    protected EnumTeam getTeam() {
        return EnumTeam.SOLO_RED_CLOSE;
    }
}
