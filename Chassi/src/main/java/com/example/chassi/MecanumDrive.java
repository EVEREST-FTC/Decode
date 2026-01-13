package com.example.chassi;

import static com.everest.constants.Constants.Elevator_tickConversion;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.everest.CommandBased.definition.Command;
import com.everest.CommandBased.definition.CommandScheduler;
import com.everest.constants.Constants.ControllerConstants;
import com.everest.constants.Constants.GyroConstants;
import com.everest.constants.meta.EnumTeam;
import com.example.chassi.command.AlignToAngle;
import com.example.chassi.roadrunner.command.RoadRunnerWrapper;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.everest.constants.Constants;

import com.everest.constants.PID;
import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.function.DoubleSupplier;

import lombok.Getter;

public final class MecanumDrive extends com.example.chassi.roadrunner.lib.MecanumDrive {

    DcMotorEx MLeve;
    Telemetry telemetry;
    private final double offset;
    @Getter
    final PID pid;

    public MecanumDrive(HardwareMap hardwareMap, Telemetry telemetry, EnumTeam team){
        super(hardwareMap,new Pose2d(0,0,0));
        /// estrutura de elevação
        MLeve = hardwareMap.get(DcMotorEx.class,"MLeve");
        MLeve.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MLeve.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.telemetry = telemetry;
        CommandScheduler.getInstance().registerSubsystem(this);
        offset = team.getOffset();
        this.pid = new PID(GyroConstants.KP, GyroConstants.KI, GyroConstants.KD);
    }
    public void drive(double x, double y, double z){
        double frontLeftPower = x+y-z;
        double frontRightPower = x-y+z;
        double backLeftPower = x-y-z;
        double backRightPower = x+y+z;
        leftFront.setPower(frontLeftPower);
        rightFront.setPower(frontRightPower);
        leftBack.setPower(backLeftPower);
        rightBack.setPower(backRightPower);

    }

    public void driveFieldRelative(double x, double y, double rotate) {
        double angle = lazyImu.get().getRobotYawPitchRollAngles().getYaw()+offset;
        angle = Math.toRadians(angle);
        double x_rotated = x * Math.cos(angle) - y * Math.sin(angle);
        double y_rotated = x * Math.sin(angle) + y * Math.cos(angle);

        drive(x_rotated, y_rotated, rotate);
    }
    public void ResetEncoder(){
        rightFront.setMode(DcMotor.RunMode.RESET_ENCODERS);
        leftFront.setMode(DcMotor.RunMode.RESET_ENCODERS);
        rightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }
    public void setPositionElevator(int alvo){
        int position = alvo* Elevator_tickConversion /360;
        MLeve.setTargetPosition(position);
        MLeve.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        MLeve.setVelocity(1000);
    }
    public void getOdoX(){
        rightFront.getCurrentPosition();
    }
    public void getOdoY(){
        leftBack.getCurrentPosition();

    }


    public void brake(){
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    public double getYaw(){
        return lazyImu.get().getRobotYawPitchRollAngles().getYaw()+offset;
    }
    public void resetIMU(){
        lazyImu.get().resetYaw();
    }

    public double DeadZone(double valor){
        if (Math.abs(valor) > ControllerConstants.DEAD_ZONE_MIN)
            return valor;
        else
            return 0;
    }

    @Override
    public void periodic() {
        //telemetry.addData("chassi-yaw-error", atSetpoint());
        //telemetry.addData("Pattern", Constants.matchPattern+" id: "+Constants.matchPattern.getAssociatedId());
    }

    public boolean atSetpoint(){
        return pid.atSetpoint();
    }

    public void stop(){
        drive(0.0, 0.0, 0.0);
    }
    TranslationalVelConstraint velConstraint(int velocity){
        return new TranslationalVelConstraint(velocity);
    }
    public Command strafeToLinearHeading(double x, double y, double angulo, int velocity){
        return new RoadRunnerWrapper(this,
                c->c.actionBuilder(
                        this.localizer.getPose()).strafeToLinearHeading(
                        new Vector2d(y,x), Math.toRadians(angulo),
                        velConstraint(velocity)));
    }
    public Command mirar(EnumTeam team, DoubleSupplier tx, DoubleSupplier distance){
        return new AlignToAngle(telemetry,
                tx, this,//chassi
                distance,
                this.getPid(),team.getIncrement(),
                team.getShortIncrement(),team.getLargeIncrement()
        );
    }

}

