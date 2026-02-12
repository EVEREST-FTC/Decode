package com.example.gate;

import com.everest.CommandBased.definition.CommandScheduler;
import com.everest.CommandBased.essentials.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class SubsystemGate extends SubsystemBase {
    DcMotorSimple ServoDor;
    Telemetry telemetry;
    public SubsystemGate(HardwareMap hardwareMap, Telemetry telemetry){
        ServoDor = hardwareMap.get(DcMotorSimple.class,"ServoDor");
        ServoDor.setDirection(DcMotorSimple.Direction.REVERSE);

        this.telemetry = telemetry;
        CommandScheduler.getInstance().registerSubsystem(this);
    }
    public void SetPowerGate(double power){
        ServoDor.setPower(power);
    }

}