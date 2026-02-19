package com.everest.plataform;

import static com.everest.constants.Constants.ControllerConstants.GAMEPAD_AIM_TRIGGER;
import static com.everest.constants.Constants.PlatformConstants.PLATFORM_MIN_ANGLE;

import com.everest.CommandBased.essentials.Trigger;
import com.everest.plataform.command.AutoLime3AC;
import com.everest.plataform.command.CalibratorCommand;
import com.everest.plataform.subsystem.SubsystemCalibrator;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

import lombok.Builder;

@Builder
public class PlatformContainer implements com.everest.constants.meta.RobotContainer {
    /// subsitema da plataforma
    private final SubsystemCalibrator subsystemCalibrator;
    /// entradas de informações vindo de outros sistemas
    private final Gamepad gamepad1;
    private final Gamepad gamepad2;
    private final Supplier<Double> distance;
    private final Supplier<Boolean> hasArtifact;


    private final Telemetry telemetry;
    @Override
    public void mainRoutine() {
        /// comando padrão
        subsystemCalibrator.setDefaultCommand(new CalibratorCommand(subsystemCalibrator, PLATFORM_MIN_ANGLE));
        /// comando de lançamento manual
        new Trigger(()->gamepad2.a).toggleOnTrue(new CalibratorCommand(subsystemCalibrator, PLATFORM_MIN_ANGLE));
        new Trigger(()->gamepad2.b).toggleOnTrue(new CalibratorCommand(subsystemCalibrator, 60));

        /// comando padrão que utiliza a camera
        new Trigger(()->gamepad1.left_trigger>GAMEPAD_AIM_TRIGGER).whileTrue(new AutoLime3AC(distance,subsystemCalibrator,telemetry));

    }

}

