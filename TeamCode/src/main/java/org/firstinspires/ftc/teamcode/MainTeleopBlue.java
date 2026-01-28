package org.firstinspires.ftc.teamcode;

import com.everest.CommandBased.definition.Command;
import com.everest.CommandBased.definition.CommandScheduler;
import com.everest.constants.meta.EnumTeam;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name =  "BLUE")
public class MainTeleopBlue extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        CommandScheduler.getInstance().reset();
        waitForStart();
        RobotContainer.builder()
                .hardwareMap(hardwareMap)
                .team(EnumTeam.SOLO_BLUE_FAR)
                .gamepad1(gamepad1)
                .gamepad2(gamepad2)
                .telemetry(telemetry)
                .build()
                .defineMainRoutine();

        while (opModeIsActive()) {
            CommandScheduler.getInstance().run();
            telemetry.addData("Comandos", CommandScheduler.getInstance().m_scheduledCommands.size());
            telemetry.update();

        }
        CommandScheduler.getInstance().reset();
    }
}
