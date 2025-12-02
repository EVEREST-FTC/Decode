package com.everest.intake;

import com.everest.constants.Constants;
import com.everest.intake.Command.CommandIntake;
import com.everest.intake.Subsystem.SubsytemIntake;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class RobotContainer {

    private final SubsytemIntake subsytemIntake;




    public RobotContainer(
            HardwareMap hardwareMap,
            Gamepad gamepad1,
            Telemetry telemetry
    ){
        this.subsytemIntake = new SubsytemIntake(
                hardwareMap,
                telemetry
        );
        triggerSelection();


    }
    private void triggerSelection(){
        subsytemIntake.setDefaultCommand(new CommandIntake(subsytemIntake, Constants.INTAKE_POWER));



    }


}
