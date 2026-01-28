package org.firstinspires.ftc.teamcode;

import static com.everest.constants.Constants.robotTimer;

import com.everest.CommandBased.definition.CommandScheduler;
import com.everest.constants.meta.EnumTeam;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "RED")
public class MainTeleopRed extends LinearOpMode {
    @Override
    public void runOpMode() {
        CommandScheduler.getInstance().reset();
        waitForStart();
        RobotContainer.builder()
                .hardwareMap(hardwareMap)
                .team(EnumTeam.SOLO_RED_FAR)
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
        //limpa o singleton no requerimento de stop
        CommandScheduler.getInstance().reset();

    }
}
