package org.firstinspires.ftc.teamcode;

import com.everest.CommandBased.compositions.ParallelRaceGroup;
import com.everest.CommandBased.compositions.SelectCommand;
import com.everest.CommandBased.compositions.SequentialCommandGroup;
import com.everest.CommandBased.util.InstantCommand;
import com.everest.constants.Constants;
import com.everest.constants.Pattern;
import com.everest.constants.meta.EnumTeam;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import java.util.Map;

@Autonomous(name = "BLUE_LONGE_SOLO")
public class BlueFarSolo extends AutonomousDefinitions {

    @Override
    protected void route() {
                new SequentialCommandGroup(
                        new InstantCommand(chassis::resetIMU),
                        obelisk(),
                        new ParallelRaceGroup(
                                counting(),
                                chassis.strafeToLinearHeading(0,-8,22.2,15)///mira 1
                        ),
                        firstLaunch(),
                        getPath()
        ).schedule();
    }

    @Override
    protected EnumTeam getTeam() {
        return EnumTeam.SOLO_BLUE_FAR;
    }

    @Override
    protected void structurePaths() {
        setPath(
                new SelectCommand<>(
                        Map.ofEntries(
                                Map.entry(Pattern.BOTTOM, new SequentialCommandGroup(
                                        chassis.strafeToLinearHeading(-12,-27,-90,30),/// coleta 1
                                        chassis.strafeToLinearHeading(-32,-27,-90,8),
                                        new ParallelRaceGroup(
                                                counting(),
                                                chassis.strafeToLinearHeading(0,-8,20,30)///mira 2
                                        ),
                                        autoLaunch(),
                                        chassis.strafeToLinearHeading(-12,-50,-90,30),/// coleta 2
                                        chassis.strafeToLinearHeading(-34,-50,-90,8),
                                        chassis.strafeToLinearHeading(-31,-61,0,30)/// final
                                )),
                                Map.entry(Pattern.MID, new SequentialCommandGroup(
                                                chassis.strafeToLinearHeading(-12,-50.5,-90,30),/// coleta 2
                                                chassis.strafeToLinearHeading(-32,-50.5,-90,8),
                                                chassis.strafeToLinearHeading(-23,-50.5,-90,30),
                                                new ParallelRaceGroup(
                                                        counting(),
                                                        chassis.strafeToLinearHeading(0,-8,20,30)///mira 2
                                                ),
                                                autoLaunch(),
                                                chassis.strafeToLinearHeading(-12,-28,-90,30),/// coleta 1
                                                chassis.strafeToLinearHeading(-34,-28,-90,8),
                                                chassis.strafeToLinearHeading(-10,-28.3,0,30)
                                )),/// final
                                Map.entry(Pattern.TOP, new SequentialCommandGroup(
                                        chassis.strafeToLinearHeading(-12,-75,-90,30),/// coleta 3
                                        chassis.strafeToLinearHeading(-32,-75,-90,8),
                                        //  chassis.strafeToLinearHeading(-22,-75,-90,30),
                                        new ParallelRaceGroup(
                                                counting(),
                                                chassis.strafeToLinearHeading(-1,-90,45,30)
                                                //chassis.strafeToLinearHeading(-12,-92,45,30)
                                                //chassis.strafeToLinearHeading(0,-8,20,30)///mira 2
                                        ),
                                        autoLaunch(),
                                        chassis.splineToLinearHeading(-12, -50.5,-90,90,-90,30),/// coleta 2
                                        chassis.strafeToLinearHeading(-34,-50.5,-90,8),
                                        chassis.strafeToLinearHeading(-31,-61,0,30)/// final
                                        /*
                                        chassis.strafeToLinearHeading(-12,-75,-90,30),/// coleta 3
                                        chassis.strafeToLinearHeading(-32,-75,-90,8),
                                        chassis.strafeToLinearHeading(-22,-75,-90,30),
                                        new ParallelRaceGroup(
                                                counting(),
                                                chassis.strafeToLinearHeading(0,-8,20,30)///mira 2
                                        ),
                                        autoLaunch(),
                                        chassis.strafeToLinearHeading(-12,-27.5,-90,30),/// coleta 1
                                        chassis.strafeToLinearHeading(-34,-27.5,-90,8),
                                        chassis.strafeToLinearHeading(-10,-28.3,0,30)/// final*/
                                        )
                                )),
                        Constants::getMatchPattern));

    }
}
