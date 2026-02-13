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

/// Utilizacao do subssitema autonomo, criando so recursos necessarios para sua rotina
@Autonomous(name = "RED_LONGE_SOLO")
public class RedFarSolo extends AutonomousDefinitions {
    public void route(){
        new SequentialCommandGroup(
                new InstantCommand(chassis::resetIMU),
                obelisk(),
                new ParallelRaceGroup(
                        counting(),
                        chassis.strafeToLinearHeading(0,-8,-20.3,15)///mira 1
                ),
                firstLaunch(3),
                getPath()
        ).schedule();
    }

    @Override
    protected void structurePaths() {
        setPath(
                new SelectCommand<>(
                Map.ofEntries(
                        Map.entry(Pattern.BOTTOM, new SequentialCommandGroup(
                                chassis.strafeToLinearHeading(17,-27,90,50),/// coleta 1
                                chassis.strafeToLinearHeading(30,-27,90,7),
                                new ParallelRaceGroup(
                                        counting(),
                                        chassis.strafeToLinearHeading(7,-81,-40,60)///mira 3
                                ),
                                autoLaunch(3),
                                chassis.strafeToLinearHeading(17,-51,90,60),/// coleta 2
                                chassis.strafeToLinearHeading(30,-51,90,7),
                                new ParallelRaceGroup(
                                        counting(),
                                        chassis.strafeToLinearHeading(7,-81,-40,60)///mira 3
                                ),
                                autoLaunch(2),
                                new ParallelCommandGroup(
                                        chassis.strafeToLinearHeading(31,-61,0,50),/// final
                                        autoLaunch(1)
                                )
                        )),
                        Map.entry(Pattern.MID, new SequentialCommandGroup(
                                chassis.strafeToLinearHeading(17,-50.5,90,60),/// coleta 2
                                chassis.strafeToLinearHeading(30,-50.5,90,7),
                                chassis.strafeToLinearHeading(23,-50.5,90,60),
                                new ParallelRaceGroup(
                                        counting(),
                                        chassis.strafeToLinearHeading(0,-8,-20,60)///mira 2
                                ),
                                autoLaunch(3),
                                chassis.strafeToLinearHeading(17,-28,90,60),/// coleta 1
                                chassis.strafeToLinearHeading(30,-28,90,7),
                                new ParallelRaceGroup(
                                        counting(),
                                        chassis.strafeToLinearHeading(0,-8,-20,60)///mira 3
                                ),
                                autoLaunch(2),
                                new ParallelCommandGroup(
                                        chassis.strafeToLinearHeading(31,-61,0,60),/// final,
                                autoLaunch(1))
                        )),
                        Map.entry(
                                Pattern.TOP, new SequentialCommandGroup(
                                        chassis.strafeToLinearHeading(17,-75,90,60),/// coleta 3
                                        chassis.strafeToLinearHeading(30,-75,90,7),

                                      //  chassis.strafeToLinearHeading(22,-75,90,50),
                                        new ParallelRaceGroup(
                                                counting(),
                                                chassis.strafeToLinearHeading(10,-90,-45,60)
                                                //chassis.strafeToLinearHeading(17,-92,-45,50)
                                                //chassis.strafeToLinearHeading(0,-8,-20,50)///mira 2
                                        ),
                                        autoLaunch(3),
                                        chassis.splineToLinearHeading(17, -50.5,90,-90,90,60),/// coleta 2
                                        chassis.strafeToLinearHeading(30,-50.5,90,7),
                                        new ParallelRaceGroup(
                                                counting(),
                                                chassis.strafeToLinearHeading(0,-8,-20,60)///mira 2
                                        ),
                                        autoLaunch(2),
                                        new ParallelCommandGroup(
                                        chassis.strafeToLinearHeading(31,-61,0,60),/// final
                                        autoLaunch(1)
                                        )
                                )
                        )),
                Constants::getMatchPattern));
    }

    @Override
    protected EnumTeam getTeam() {
        return EnumTeam.SOLO_RED_FAR;
    }
}
