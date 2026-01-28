package org.firstinspires.ftc.teamcode;

import com.everest.CommandBased.compositions.ParallelCommandGroup;
import com.everest.CommandBased.compositions.ParallelRaceGroup;
import com.everest.CommandBased.compositions.RepeatCommand;
import com.everest.CommandBased.compositions.SequentialCommandGroup;
import com.everest.CommandBased.util.ConditionalCommand;
import com.everest.CommandBased.util.InstantCommand;
import com.everest.constants.Constants;
import com.everest.constants.meta.EnumTeam;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "BLUE_LONGE_SOLO")
public class BlueFarSolo extends AutonomousOptimized{

    @Override
    protected void route() {
        new ParallelCommandGroup(
                new SequentialCommandGroup(
                        new InstantCommand(chassis::resetIMU),
                        obelisk(),
                        new ParallelRaceGroup(
                                counting(),
                                chassis.strafeToLinearHeading(0,-8,22.2,30)///mira 1
                        ),
                        firstLaunch(),
                        new  ParallelRaceGroup(
                            new SequentialCommandGroup(
                                    chassis.strafeToLinearHeading(-10,-27.7,-90,30),/// coleta 1
                                    chassis.strafeToLinearHeading(-31,-27.7,-90,10)
                            ).antesDe(new ConditionalCommand(()-> Constants.getMatchPattern().ordinal() == 1)),


                            new SequentialCommandGroup(
                                    chassis.strafeToLinearHeading(-5,-50,-90,30),/// coleta 2
                                    chassis.strafeToLinearHeading(-31,-50,-90,10)
                            ).antesDe(new ConditionalCommand(()-> Constants.getMatchPattern().ordinal() == 2)),

                            new SequentialCommandGroup(
                                    chassis.strafeToLinearHeading(-5,-73.4,-90,30),/// coleta 3
                                    chassis.strafeToLinearHeading(-31,-73.4,-90,10)
                            ).antesDe(new ConditionalCommand(()-> Constants.getMatchPattern().ordinal() == 3))
                        ),



                        new ParallelRaceGroup(
                                counting(),
                                chassis.strafeToLinearHeading(0,-8,23.5,50)///mira 2
                        ),
                        autoLaunch(),


                        chassis.strafeToLinearHeading(-10,-28.3,0,30)/// final*/





                ),
                new RepeatCommand(new InstantCommand(subsystemOuttake::artifacts))
        ).schedule();
    }

    @Override
    protected EnumTeam getTeam() {
        return EnumTeam.SOLO_BLUE_FAR;
    }
}
