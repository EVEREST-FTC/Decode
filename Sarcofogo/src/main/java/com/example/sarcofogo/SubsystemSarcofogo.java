package com.example.sarcofogo;


import static com.everest.constants.Constants.SarcofagoConstants.SARCOFOGO_MAX_ANGLE;
import static com.everest.constants.Constants.SarcofagoConstants.SARCOFOGO_MAX_SERVO_ANGLE;
import static com.everest.constants.Constants.SarcofagoConstants.SARCOFOGO_MIN_ANGLE;
import static com.everest.constants.Constants.SarcofagoConstants.SarcofogoInitialPosition;

import com.everest.CommandBased.definition.CommandScheduler;
import com.everest.CommandBased.essentials.SubsystemBase;
import com.everest.constants.Constants;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import lombok.Getter;
import lombok.Setter;

@Getter
public class SubsystemSarcofogo extends SubsystemBase {


    Servo ServoSarcofogo;
    RevColorSensorV3 SensorSarcofogo;
    Telemetry telemetry;
    double position;
    @Setter
    Moment moment = Moment.KEEP;

    private int memore;
    public SubsystemSarcofogo(HardwareMap hardwareMap, Telemetry telemetry){
        ServoSarcofogo = hardwareMap.get(Servo.class,"ServoSarcofogo");
        SensorSarcofogo = hardwareMap.get(RevColorSensorV3.class,"SensorSarcofogo");
        resetPosiiton();
        resetmemore();

        this.telemetry = telemetry;
        CommandScheduler.getInstance().registerSubsystem(this);
    }

    public boolean getsensorSarcofogo(){
        if (SensorSarcofogo.getDistance(DistanceUnit.MM) < 34) memore += 1;
        return memore > 2;
    }
    public  void resetmemore(){
        memore = 0;
    }


    public void setPositionGate(double alvo){
        position = limiter(alvo)/SARCOFOGO_MAX_SERVO_ANGLE;
        ServoSarcofogo.setPosition(1 - position);
    }
    public void resetPosiiton(){
        setPositionGate(SarcofogoInitialPosition);
    }
    private double limiter(double angle){
        if(angle> SARCOFOGO_MAX_ANGLE)
            return SARCOFOGO_MAX_ANGLE;
        else return Math.max(angle,SARCOFOGO_MIN_ANGLE);
    }
    public boolean isSending(){
        return moment == Moment.SEND;
    }

    @Override
    public void periodic() {
        telemetry.addData("momento:" , moment);
        telemetry.addData("sensor", getsensorSarcofogo());
    }
}