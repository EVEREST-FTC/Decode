package org.firstinspires.ftc.teamcode;

import com.everest.CommandBased.definition.CommandScheduler;
import com.everest.constants.meta.EnumTeam;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "REDLONGE")
public class MainAutoRedFar extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        CommandScheduler.getInstance().cancelAll();
        new AutonomousContainer(
                hardwareMap,
                telemetry,
                EnumTeam.SOLO_RED_FAR)
                .mainRoutine();
        //delay p construção dos subsistemas
        waitForStart();


        while (opModeIsActive()) {
            CommandScheduler.getInstance().run();
            telemetry.addData("Comandos", CommandScheduler.getInstance().m_scheduledCommands.size());
            telemetry.update();
        }
        //limpa o singleton no requerimento de stop
        if(isStopRequested()){
            CommandScheduler.getInstance().cancelAll();
            CommandScheduler.getInstance().unregisterAllSubsystems();
        }
    }
}
