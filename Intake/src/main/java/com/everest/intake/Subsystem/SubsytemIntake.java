package com.everest.intake.Subsystem;

import com.everest.CommandBased.definition.CommandScheduler;
import com.everest.CommandBased.essentials.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class SubsytemIntake extends SubsystemBase {
    DcMotor Mintake;

    public SubsytemIntake(HardwareMap hardwareMap, Telemetry telemetry){
        Mintake = hardwareMap.get(DcMotor.class,"Mintake");
        CommandScheduler.getInstance().registerSubsystem(this);
    }
    public void startIntake(double power){
        Mintake.setPower(power);
    }
}
