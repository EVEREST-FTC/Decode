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
                                        chassis.strafeToLinearHeading(-14,-28.2,-90,50),/// coleta 1
                                        chassis.strafeToLinearHeading(-33,-28.2,-90,10),


                                        chassis.strafeToLinearHeading(0,-8,20.3,30),///mira 1
                                        autoLaunch(false),

                                        chassis.strafeToLinearHeading(-14,-52.5,-90,45),/// coleta 2
                                        chassis.strafeToLinearHeading(-33,-52.5,-90,10),


                                        chassis.strafeToLinearHeading(-31,-61,0,50)/// final
                                )),
                                Map.entry(Pattern.MID, new SequentialCommandGroup(
                                        chassis.strafeToLinearHeading(-14,-52.5,-90,45),/// coleta 2
                                        chassis.strafeToLinearHeading(-33,-52.5,-90,10),


                                        chassis.splineToLinearHeading(-0, -8,20.3,90,0,45),/// coleta 2
                                        autoLaunch(false),

                                        chassis.strafeToLinearHeading(-10,-75.5,-90,50),/// coleta 3
                                        chassis.strafeToLinearHeading(-30,-75.5,-90,10),


                                        chassis.strafeToLinearHeading(-31,-61,0,45)/// final,
                                )),
                                Map.entry(
                                        Pattern.TOP, new SequentialCommandGroup(
                                                chassis.strafeToLinearHeading(-14,-75.5,-90,45),/// coleta 3
                                                chassis.strafeToLinearHeading(-33,-75.5,-90,10),


                                                chassis.strafeToLinearHeading(-10,-90,47,60),
                                                autoLaunch(false),


                                                chassis.splineToLinearHeading(-14, -53,-90,90,-90,45),/// coleta 2
                                                chassis.strafeToLinearHeading(-33,-53,-90,10),


                                                chassis.strafeToLinearHeading(-31,-61,0,45)/// final

                                        )
                                )),
                        Constants.AutoConstants::getMatchPattern));
}}
