package org.firstinspires.ftc.teamcode.auto;

import com.everest.CommandBased.definition.CommandScheduler;
import com.everest.constants.EnumTeam;
import com.example.chassi.Auto.RobotContainer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
@Autonomous
public class MainAuto extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        new RobotContainer(
                hardwareMap,
                telemetry,
                EnumTeam.BLUE

        );
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
