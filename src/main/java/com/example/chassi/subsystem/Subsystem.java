package com.example.chassi.subsystem;

import com.everest.CommandBased.definition.CommandScheduler;
import com.everest.CommandBased.essentials.SubsystemBase;
import com.example.chassi.Constants;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Subsystem extends SubsystemBase {
    DcMotorEx MFR,MFL,MBR,MBL;
    IMU imu;
    Telemetry telemetry;

    public Subsystem(HardwareMap hardwareMap, Telemetry telemetry){
        MFL = hardwareMap.get(DcMotorEx.class,"MFL");
        MFR = hardwareMap.get(DcMotorEx.class,"MFR");
        MBL = hardwareMap.get(DcMotorEx.class,"MBL");
        MBR = hardwareMap.get(DcMotorEx.class,"MBR");
        MFL.setDirection(DcMotorSimple.Direction.REVERSE);
        MBL.setDirection(DcMotorSimple.Direction.REVERSE);
        imu = hardwareMap.get(IMU.class, "imu");
        MFR.setMode(DcMotor.RunMode.RESET_ENCODERS);
        MBL.setMode(DcMotor.RunMode.RESET_ENCODERS);
        MFR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        MBL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        this.telemetry = telemetry;
        telemetry.setMsTransmissionInterval(11);

        CommandScheduler.getInstance().registerSubsystem(this);
    }
    public void drive(double y, double x, double z){
        double frontLeftPower = y+x+z;
        double frontRightPower = y-x-z;
        double backLeftPower = y-x+z;
        double backRightPower = y+x-z;
        MFL.setPower(frontLeftPower);
        MFR.setPower(frontRightPower);
        MBL.setPower(backLeftPower);
        MBR.setPower(backRightPower);
    }

    public void brake(){
        MFR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        MFL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        MBR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        MBL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


    }
    @Override
    public void periodic() {
        telemetry.addData("velocidadeL", (MFL.getVelocity()/ Constants.TickConversion)*60);
        telemetry.addData("velocidadeF", (MFR.getVelocity()/Constants.TickConversion)*60);
        telemetry.addData("positionL",MFL.getCurrentPosition()/ Constants.ConversionFactorRodas);
        telemetry.addData("positionR",MFR.getCurrentPosition()/Constants.ConversionFactorRodas);
    }
    public double deadzone(double valor){
        if (Math.abs(valor) < Constants.deadzonelimit)
            return 0;
        else
            return valor;
    }


    public void stop(){
        drive(0.0, 0.0, 0.0);
    }
}
