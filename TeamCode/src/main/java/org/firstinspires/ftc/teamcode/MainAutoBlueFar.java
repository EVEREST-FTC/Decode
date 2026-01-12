package org.firstinspires.ftc.teamcode;

import com.everest.CommandBased.definition.CommandScheduler;
import com.everest.constants.meta.EnumTeam;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "BLUELONGE")
public class MainAutoBlueFar extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        new AutonomousContainer(
                hardwareMap,
                telemetry,
                EnumTeam.SOLO_BLUE_FAR)
                .mainRoutine();
        //delay p construção dos subsistemas
        waitForStart();

        while (opModeIsActive()) {
            CommandScheduler.getInstance().run();
            telemetry.update();
        }
        //limpa o singleton no requerimento de stop
        CommandScheduler.getInstance().m_scheduledCommands.clear();
    }
}
