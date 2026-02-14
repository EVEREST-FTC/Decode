package org.firstinspires.ftc.teamcode;

import com.everest.CommandBased.essentials.Trigger;
import com.everest.constants.meta.EnumTeam;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous
public class AutoTest extends AutonomousDefinitions{
    @Override
    protected void route() {
        new Trigger(()->gamepad1.a).whileTrue(
                chassis.strafeToLinearHeading(
                        10,
                        20,
                        0,
                        20)
        );
    }

    @Override
    protected EnumTeam getTeam() {
        return EnumTeam.SOLO_BLUE_FAR;
    }

    @Override
    protected void structurePaths() {

    }
}
