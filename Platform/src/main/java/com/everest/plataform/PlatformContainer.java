package com.everest.plataform;

import com.everest.CommandBased.essentials.Trigger;
import com.everest.plataform.command.AutoLime3AC;
import com.everest.plataform.command.CalibratorCommand;
import com.everest.plataform.subsystem.SubsystemCalibrator;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.function.Supplier;

import lombok.Builder;

@Builder
public class PlatformContainer implements com.everest.constants.meta.RobotContainer {

    private final SubsystemCalibrator subsystemCalibrator;
    private final Gamepad gamepad;

    private final Supplier<Double> distancia;
    private final Supplier<Boolean> hasArtifact;

    private final Telemetry telemetry;
    @Override
    public void mainRoutine() {
        subsystemCalibrator.setDefaultCommand(new AutoLime3AC(distancia,subsystemCalibrator,telemetry));
        /*new Trigger(hasArtifact::get).whileFalse(new CalibratorCommand(subsystemCalibrator,45));*/

    }
}

