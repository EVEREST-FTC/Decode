package com.everest.plataform;

import com.everest.CommandBased.essentials.Trigger;
import com.everest.plataform.command.AutoLime3AC;
import com.everest.plataform.command.CalibratorCommand;
import com.everest.plataform.subsystem.SubsystemCalibrator;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.function.Supplier;

public class RobotContainer {

    private final SubsystemCalibrator subsystemCalibrator;
    private final Gamepad gamepad;

    private final Supplier<Double> distancia;

    private final Telemetry telemetry;



    public RobotContainer(
            HardwareMap hardwareMap,
            Gamepad gamepad1,
            Telemetry telemetry, Supplier<Double> distancia
    ) {
        this.gamepad = gamepad1;
        this.subsystemCalibrator = new SubsystemCalibrator(
                hardwareMap,
                telemetry
        );
        this.telemetry = telemetry;
        this.distancia = distancia;

        robottest();
    }

    private void robottest(){
        subsystemCalibrator.setDefaultCommand(new AutoLime3AC(distancia,subsystemCalibrator,telemetry));
    }

    private void triggerSelection(){

        new Trigger(()-> gamepad.dpad_up).onTrue(
                new CalibratorCommand(subsystemCalibrator,55)
        );
        new Trigger(()-> gamepad.dpad_left).onTrue(
                new CalibratorCommand(subsystemCalibrator,50)
        );
        new Trigger(()-> gamepad.dpad_down).onTrue(
                new CalibratorCommand(subsystemCalibrator,45)
        );
        new Trigger(()-> gamepad.a).onTrue(
                new CalibratorCommand(subsystemCalibrator,60)
        );
        new Trigger(()-> gamepad.b).onTrue(
                new CalibratorCommand(subsystemCalibrator,65)
        );


    }

}

