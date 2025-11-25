package com.example.sarcofogo;

import com.everest.CommandBased.essentials.Trigger;
import com.everest.constants.Constants;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class RobotContainer {
    private final Gamepad gamepad;
    private final SubsystemSarcofogo subsystemSarcofogo;



    public RobotContainer(HardwareMap hardwareMap,
                          Telemetry telemetry,
                          Gamepad gamepad, SubsystemSarcofogo subsystemSarcofogo) {

        this.gamepad = gamepad;
        this.subsystemSarcofogo = subsystemSarcofogo;

        triggerAssociations();
    }

    private void triggerAssociations(){
        new Trigger(()->gamepad.dpad_up).toggleOnTrue(
                new Command(subsystemSarcofogo,90).espere(1, Constants.clockSeconds)

        );
    }
}
