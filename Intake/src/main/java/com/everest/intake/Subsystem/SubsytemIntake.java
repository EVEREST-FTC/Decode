package com.everest.intake.Subsystem;

import static com.everest.constants.Constants.PlatformConstants.MAX_RPM;

import com.everest.CommandBased.definition.CommandScheduler;
import com.everest.CommandBased.essentials.SubsystemBase;
import com.everest.constants.Constants;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import lombok.Getter;
import lombok.Setter;

public class SubsytemIntake extends SubsystemBase {
    DcMotorEx Mintake;
    Telemetry telemetry;
    @Getter
    @Setter
    private boolean isActive = true;
    public SubsytemIntake(HardwareMap hardwareMap, Telemetry telemetry){
        Mintake = hardwareMap.get(DcMotorEx.class,"Mintake");
        this.telemetry = telemetry;
        CommandScheduler.getInstance().registerSubsystem(this);
        Mintake.setVelocityPIDFCoefficients(10, 3, 0, 0);
    }
    public void startIntake(double power){
        Mintake.setVelocity(power*MAX_RPM*Constants.IntakeConstants.REVERSE_TICK_CONVERSION);
    }
    public double getIntakevelocit(){
        double velocidade = Mintake.getVelocity();
        return velocidade/ Constants.IntakeConstants.REVERSE_TICK_CONVERSION;
    }
    public void Braker(){
        Mintake.setPower(0);
        Mintake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void periodic() {
        /*telemetry.addData("velociadeintake",getIntakevelocit());*/
    }
}
