package org.firstinspires.ftc.teamcode;

import com.everest.CommandBased.definition.CommandScheduler;
import com.everest.constants.meta.EnumTeam;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
@Autonomous(name = "REDLONGE")
public class MainAutoRed extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        new AutonomousRoutine(
                hardwareMap,
                telemetry,
                EnumTeam.RED);
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
