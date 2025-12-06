package org.firstinspires.ftc.teamcode;

import com.everest.CommandBased.definition.CommandScheduler;
import com.everest.constants.meta.EnumTeam;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "RED")
public class Red extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        RobotContainer.builder()
                .hardwareMap(hardwareMap)
                .team(EnumTeam.RED)
                .gamepad1(gamepad1)
                .telemetry(telemetry)
                .build()
                .defineMainRoutine();
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
