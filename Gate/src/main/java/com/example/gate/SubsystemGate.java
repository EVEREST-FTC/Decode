package com.example.gate;

import static com.everest.constants.Constants.GateConstants.GATE_MAX_ANGLE;
import static com.everest.constants.Constants.GateConstants.GATE_MAX_SERVO_ANGLE;
import static com.everest.constants.Constants.GateConstants.GATE_MIN_ANGLE;
import static com.everest.constants.Constants.GateConstants.GateClosePosition;

import com.everest.CommandBased.definition.CommandScheduler;
import com.everest.CommandBased.essentials.SubsystemBase;
import com.everest.constants.Constants;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class SubsystemGate extends SubsystemBase {
    Servo ServoDor;
    Telemetry telemetry;
    double position;
    public SubsystemGate(HardwareMap hardwareMap, Telemetry telemetry){
        ServoDor = hardwareMap.get(Servo.class,"ServoDor");
        resetPosiiton();
        this.telemetry = telemetry;
        CommandScheduler.getInstance().registerSubsystem(this);
    }
    public void setPositionGate(double alvo){
        position = limiter(alvo)/ GATE_MAX_SERVO_ANGLE;
        ServoDor.setPosition(1 - position);
    }
    public void resetPosiiton(){
        setPositionGate(GateClosePosition);
    }
    private double limiter(double angle){
        if(angle> GATE_MAX_ANGLE)
            return GATE_MAX_ANGLE;
        else return Math.max(angle,GATE_MIN_ANGLE);
    }

}