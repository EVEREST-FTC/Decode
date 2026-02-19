package org.firstinspires.ftc.teamcode;

import com.everest.CommandBased.compositions.ParallelRaceGroup;
import com.everest.CommandBased.compositions.SequentialCommandGroup;
import com.everest.CommandBased.util.InstantCommand;
import com.everest.CommandBased.util.WaitCommand;
import com.everest.constants.Constants;
import com.everest.constants.meta.EnumTeam;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/// Utilizacao do subssitema autonomo, criando so recursos necessarios para sua rotina
@Autonomous(name = "RED_LONGE_FALCONS")
public class RedFarFalcons extends AutonomousDefinitions {

    public void route(){
        new SequentialCommandGroup(
                new InstantCommand(chassis::resetIMU),
                obelisk(),
                new ParallelRaceGroup(
                        counting(),
                        chassis.strafeToLinearHeading(0,-8,-20.3,5)///mira 1
                ),
                firstLaunch(3),

                chassis.strafeToLinearHeading(17,-28,90,60),/// coleta 1
                chassis.strafeToLinearHeading(30,-28,90,7),

                new ParallelRaceGroup(
                        counting(),
                        chassis.strafeToLinearHeading(0,-8,-20,40)///mira 2
                ),
                autoLaunch(3),

               /* chassis.strafeToLinearHeading(12,-51,90,50),/// coleta 2
                chassis.strafeToLinearHeading(32,-51,90,13),*/

                /*new ParallelRaceGroup(
                        counting(),
                        chassis.strafeToLinearHeading(0,-8,-23,50)///mira 3
                ),*/
                /*autoLaunch(),*/
                new WaitCommand(9, Constants.robotTimer),
                chassis.strafeToLinearHeading(10,-28.3,0,40)/// final*/,,



        ).schedule();
    }

    @Override
    protected void structurePaths() {}

    @Override
    protected EnumTeam getTeam() {
        return EnumTeam.SOLO_RED_FAR;
    }
}
