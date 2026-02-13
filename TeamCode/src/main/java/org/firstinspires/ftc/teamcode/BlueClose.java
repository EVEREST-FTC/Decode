package org.firstinspires.ftc.teamcode;

import com.everest.CommandBased.compositions.ParallelRaceGroup;
import com.everest.CommandBased.compositions.SequentialCommandGroup;
import com.everest.CommandBased.definition.Command;
import com.everest.CommandBased.util.InstantCommand;
import com.everest.CommandBased.util.WaitCommand;
import com.everest.constants.Constants;
import com.everest.constants.Pattern;
import com.everest.constants.meta.EnumTeam;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Disabled
@Autonomous(name = "BLUEPERTO")
public class BlueClose extends AutonomousDefinitions {
    @Override
    protected void route() {
        new SequentialCommandGroup(
                new InstantCommand(()-> subLime.pipelineSwitch(2)),
                new InstantCommand(chassis::resetIMU),
                new ParallelRaceGroup(
                        chassis.strafeToLinearHeading(10,45,0,32),
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
                chassis.strafeToLinearHeading(10,45,27,32),/// mira 1
                firstLaunch(3),
                new WaitCommand(0.5,Constants.robotTimer),
                chassis.strafeToLinearHeading(10,50,-90,15),/// coleta 1
                chassis.strafeToLinearHeading(-17,50,-90,10),
                new ParallelRaceGroup(
                        chassis.strafeToLinearHeading(10,45,32,32),/// mira 2
                        counting()
                ),
                autoLaunch(3),

                chassis.strafeToLinearHeading(10,75,-90,24),/// coleta 2
                chassis.strafeToLinearHeading(-17,75,-90,10),

                new ParallelRaceGroup(
                        chassis.strafeToLinearHeading(10,45,32,32),/// mira 3
                        counting()
                ),
                autoLaunch(3),

                chassis.strafeToLinearHeading(-11,60,0,32)/// final




        ).schedule();
    }

    @Override
    protected void structurePaths() {}

    @Override
    protected EnumTeam getTeam() {
        return EnumTeam.SOLO_BLUE_CLOSE;
    }
}
