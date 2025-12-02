package com.example.chassi;

import com.everest.CommandBased.definition.CommandScheduler;
import com.everest.CommandBased.essentials.SubsystemBase;
import com.everest.constants.Constants;
import com.everest.constants.EnumTeam;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Deprecated
public final class Chassi extends SubsystemBase{

    DcMotorEx MFR,MFL,MBR,MBL,MLeve;
    IMU imu;

    Telemetry telemetry;
    private final double offset;

    public Chassi(HardwareMap hardwareMap, Telemetry telemetry, EnumTeam team){
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


        /// estrutura de elevação
        MLeve = hardwareMap.get(DcMotorEx.class,"MLeve");
        MLeve.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MLeve.setMode(DcMotor.RunMode.RUN_USING_ENCODER);







        this.telemetry = telemetry;

        RevHubOrientationOnRobot.LogoFacingDirection logo = RevHubOrientationOnRobot.LogoFacingDirection.RIGHT;
        RevHubOrientationOnRobot.UsbFacingDirection usbFacingDirection = RevHubOrientationOnRobot.UsbFacingDirection.UP;
        RevHubOrientationOnRobot orientationOnRobot = new RevHubOrientationOnRobot(
                logo, usbFacingDirection
        );
        imu.initialize(
                new IMU.Parameters(
                        orientationOnRobot
                )
        );

        CommandScheduler.getInstance().registerSubsystem(this);

        offset = team.getOffset();
    }
    public void drive(double x, double y, double z){
        double frontLeftPower = x+y+z;
        double frontRightPower = x-y-z;
        double backLeftPower = x-y+z;
        double backRightPower = x+y-z;
        MFL.setPower(frontLeftPower);
        MFR.setPower(frontRightPower);
        MBL.setPower(backLeftPower);
        MBR.setPower(backRightPower);
    }

    public void driveFieldRelative(double x, double y, double rotate) {
        double angle = imu.getRobotYawPitchRollAngles().getYaw()+offset;
        angle = Math.toRadians(angle);
        double x_rotated = x * Math.cos(angle) - y * Math.sin(angle);
        double y_rotated = x * Math.sin(angle) + y * Math.cos(angle);

        drive(x_rotated, y_rotated, rotate);
    }
    public void ResetEncoder(){
        MFR.setMode(DcMotor.RunMode.RESET_ENCODERS);
        MFL.setMode(DcMotor.RunMode.RESET_ENCODERS);
        MFR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        MFL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }
    public void setPositionEleveitor(int alvo){
        int position = alvo*Constants.Eleveitor_tickConversion/360;
        MLeve.setTargetPosition(position);
        MLeve.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        MLeve.setVelocity(1000);
    }


    public void brake(){
        MFR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        MFL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        MBR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        MBL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    public double getYaw(){
        return imu.getRobotYawPitchRollAngles().getYaw()+offset;
    }
    public void resetIMU(){
        imu.resetYaw();
    }

    @Override
    public void periodic() {
        telemetry.addData("angle", getYaw());
        telemetry.addData("MotorElevaçãoPosição",MLeve.getCurrentPosition());
        telemetry.addData("erro",MLeve.getTargetPosition()-MLeve.getCurrentPosition());
    }

    public void stop(){
        drive(0.0, 0.0, 0.0);
    }
}
