package org.firstinspires.ftc.teamcode;

import com.everest.CommandBased.compositions.ParallelCommandGroup;
import com.everest.CommandBased.compositions.ParallelRaceGroup;
import com.everest.CommandBased.compositions.RepeatCommand;
import com.everest.CommandBased.compositions.SequentialCommandGroup;
import com.everest.CommandBased.util.InstantCommand;
import com.everest.CommandBased.util.WaitCommand;
import com.everest.constants.Constants;
import com.everest.constants.meta.EnumTeam;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "BLUE_LONGE_FALCONS")
public class BlueFarFalcons extends AutonomousDefinitions {

    @Override
    protected void route() {
        new ParallelCommandGroup(
                new SequentialCommandGroup(
                        new InstantCommand(chassis::resetIMU),
                        obelisk(),
                        new ParallelRaceGroup(
                                counting(),
                                chassis.strafeToLinearHeading(0,-8,22.2,5)///mira 1
                        ),

                        firstLaunch(3),

                        chassis.strafeToLinearHeading(-17,-28,-90,60),/// coleta 1
                        chassis.strafeToLinearHeading(-30,-28,-90,7),

                        new ParallelRaceGroup(
                                counting(),
                                chassis.strafeToLinearHeading(0,-8,23.5,50)///mira 2
                        ),
                        autoLaunch(3),
/*

                        chassis.strafeToLinearHeading(-5,-50,-90,50),/// coleta 2
                        chassis.strafeToLinearHeading(-31,-50,-90,10),
*/

                        /*new ParallelRaceGroup(
                                counting(),
                                chassis.strafeToLinearHeading(0,-8,23.5,50)///mira 3
                        ),  
                        autoLaunch(),*/
                        new WaitCommand(9, Constants.robotTimer),
                        chassis.strafeToLinearHeading(-10,-28.3,0,30)/// final*/
                ),
                new RepeatCommand(new InstantCommand(subsystemOuttake::artifacts))
        ).schedule();
    }

    @Override
    protected void structurePaths() {

    }

    @Override
    protected EnumTeam getTeam() {
        return EnumTeam.SOLO_BLUE_FAR;
    }
}
