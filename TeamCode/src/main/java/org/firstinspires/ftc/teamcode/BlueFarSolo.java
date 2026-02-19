package org.firstinspires.ftc.teamcode;

import com.everest.CommandBased.compositions.ParallelCommandGroup;
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
                        firstLaunch(3),
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
                                        chassis.strafeToLinearHeading(-17,-28,-90,60),/// coleta 1
                                        chassis.strafeToLinearHeading(-30,-28,-90,7),
                                        new ParallelRaceGroup(
                                                counting(),
                                                chassis.strafeToLinearHeading(-7,-81,40,60)///mira 3
                                        ),
                                        autoLaunch(3),
                                        chassis.strafeToLinearHeading(-10,-51,-90,40),/// coleta 2
                                        chassis.strafeToLinearHeading(-30,-51,-90,7),
                                        new ParallelRaceGroup(
                                                counting(),
                                                chassis.strafeToLinearHeading(-4,-81,40,60)///mira 3
                                        ),
                                        autoLaunch(2),
                                        new ParallelCommandGroup(
                                                chassis.strafeToLinearHeading(-31,-61,0,50),/// final
                                                autoLaunch(1)
                                        )
                                )),
                                Map.entry(Pattern.MID, new SequentialCommandGroup(
                                        chassis.strafeToLinearHeading(-13,-52.3,-90,60),/// coleta 2
                                        chassis.strafeToLinearHeading(-30,-52.3,-90,7),
                                        new ParallelRaceGroup(
                                                counting(),
                                                chassis.splineToLinearHeading(0, -8,18,90,-20,60)
                                              ///mira 2
                                        ),
                                        autoLaunch(3),
                                        chassis.strafeToLinearHeading(-12,-28,-90,60),/// coleta 1
                                        chassis.strafeToLinearHeading(-29,-28,-90,7),
                                        new ParallelRaceGroup(
                                                counting(),
                                                chassis.strafeToLinearHeading(0,-8,20,60)///mira 3
                                        ),
                                        autoLaunch(2),
                                        new ParallelCommandGroup(
                                                chassis.strafeToLinearHeading(-31,-61,0,60),
                                                autoLaunch(1))/// final,
                                )),
                                Map.entry(
                                        Pattern.TOP, new SequentialCommandGroup(
                                                chassis.strafeToLinearHeading(-13,-75,-90,60),/// coleta 3
                                                chassis.strafeToLinearHeading(-30,-75,-90,7),

                                                //  chassis.strafeToLinearHeading(22,-75,90,50),
                                                new ParallelRaceGroup(
                                                        counting(),
                                                        chassis.strafeToLinearHeading(-10,-90,45,60)
                                                        //chassis.strafeToLinearHeading(17,-92,-45,50)
                                                        //chassis.strafeToLinearHeading(0,-8,-20,50)///mira 2
                                                ),
                                                autoLaunch(3),
                                                chassis.splineToLinearHeading(-13, -52.3,-90,90,-90,60),/// coleta 2
                                                chassis.strafeToLinearHeading(-30,-52.3,-90,7),
                                                new ParallelRaceGroup(
                                                        counting(),
                                                        chassis.strafeToLinearHeading(-10,-90,45,60)///mira 2
                                                ),
                                                autoLaunch(2),
                                                new ParallelCommandGroup(
                                                chassis.strafeToLinearHeading(-31,-61,0,60),/// final
                                                        autoLaunch(1)
                                                )
                                        )
                                )),
                        Constants.AutoConstants::getMatchPattern));
}}
