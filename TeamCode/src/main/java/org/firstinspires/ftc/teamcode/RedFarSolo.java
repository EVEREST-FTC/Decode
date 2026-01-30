package org.firstinspires.ftc.teamcode;

import com.everest.CommandBased.compositions.ParallelRaceGroup;
import com.everest.CommandBased.compositions.SequentialCommandGroup;
import com.everest.CommandBased.util.ConditionalCommand;
import com.everest.CommandBased.util.InstantCommand;
import com.everest.constants.Constants;
import com.everest.constants.meta.EnumTeam;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
/// Utilizacao do subssitema autonomo, criando so recursos necessarios para sua rotina
@Autonomous(name = "RED_LONGE_SOLO")
public class RedFarSolo extends AutonomousOptimized {

    public void route(){

        new SequentialCommandGroup(
                new InstantCommand(chassis::resetIMU),
                obelisk(),
                new ParallelRaceGroup(
                        counting(),
                        chassis.strafeToLinearHeading(0,-8,-20.3,15)///mira 1
                ),
                firstLaunch(),

                new ParallelRaceGroup(
                    new SequentialCommandGroup(
                        chassis.strafeToLinearHeading(12,-27,90,30),/// coleta 1
                        chassis.strafeToLinearHeading(32,-27,90,8),
                            new ParallelRaceGroup(
                                    counting(),
                                    chassis.strafeToLinearHeading(0,-8,-20,30)///mira 2
                            ),
                            autoLaunch(),
                            chassis.strafeToLinearHeading(12,-50,90,30),/// coleta 2
                            chassis.strafeToLinearHeading(34,-50,90,8),
                            chassis.strafeToLinearHeading(31,-61,0,30)/// final
                    ).antesDe(new ConditionalCommand(()->Constants.getMatchPattern().ordinal() == 0)),

                    new SequentialCommandGroup(
                            chassis.strafeToLinearHeading(12,-50.5,90,30),/// coleta 2
                            chassis.strafeToLinearHeading(32,-50.5,90,8),
                            chassis.strafeToLinearHeading(23,-50.5,90,30),
                            new ParallelRaceGroup(
                                    counting(),
                                    chassis.strafeToLinearHeading(0,-8,-20,30)///mira 2
                            ),
                            autoLaunch(),
                            chassis.strafeToLinearHeading(12,-28,90,30),/// coleta 1
                            chassis.strafeToLinearHeading(34,-28,90,8),
                            chassis.strafeToLinearHeading(10,-28.3,0,30)/// final
                    ).antesDe(new ConditionalCommand(()->Constants.getMatchPattern().ordinal() == 1)),

                    new SequentialCommandGroup(
                            chassis.strafeToLinearHeading(12,-75,90,30),/// coleta 3
                            chassis.strafeToLinearHeading(32,-75,90,8),
                            chassis.strafeToLinearHeading(22,-75,90,30),
                            new ParallelRaceGroup(
                                    counting(),
                                    chassis.strafeToLinearHeading(0,-8,-20,30)///mira 2
                            ),
                            autoLaunch(),
                            chassis.strafeToLinearHeading(12,-27.5,90,30),/// coleta 1
                            chassis.strafeToLinearHeading(34,-27.5,90,8),
                            chassis.strafeToLinearHeading(10,-28.3,0,30)/// final
                    ).antesDe(new ConditionalCommand(()->Constants.getMatchPattern().ordinal() == 2))
                )
        ).schedule();
    }

    @Override
    protected EnumTeam getTeam() {
        return EnumTeam.SOLO_RED_FAR;
    }
}
