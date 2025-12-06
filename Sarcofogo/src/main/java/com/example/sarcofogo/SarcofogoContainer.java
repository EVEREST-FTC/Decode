package com.example.sarcofogo;

import com.everest.CommandBased.essentials.Trigger;
import com.everest.constants.Constants;
import com.qualcomm.robotcore.hardware.Gamepad;

import lombok.Builder;

@Builder
public class SarcofogoContainer implements com.everest.constants.meta.RobotContainer {
    private final Gamepad gamepad;
    private final SubsystemSarcofogo subsystemSarcofogo;
    @Override
    public void mainRoutine() {
        new Trigger(()->gamepad.dpad_up).toggleOnTrue(
                new Command(subsystemSarcofogo,90).espere(1, Constants.clockSeconds)
        );
    }
}
