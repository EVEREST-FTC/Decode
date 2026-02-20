package com.example.gate;

import static com.everest.constants.Constants.robotTimer;

import com.everest.CommandBased.definition.Clock;
import com.everest.CommandBased.definition.Command;
import com.everest.constants.ClockAdapter;
import com.qualcomm.robotcore.util.ElapsedTime;

public class TimeredCommand extends Command {
    private final SubsystemGate subsystemGate;
    private final double OpenPower,ClosePower;
    ElapsedTime elapsedTime;
    public TimeredCommand(SubsystemGate subsystemGate, double OpenPower, double closePower) {
        this.subsystemGate = subsystemGate;
        this.OpenPower = OpenPower;
        ClosePower = closePower;
        elapsedTime = new ElapsedTime(ElapsedTime.Resolution.SECONDS);
        addRequirements(subsystemGate);

    }

    @Override
    public void initialize() {
        elapsedTime.reset();
    }

    @Override
    public void execute() {
        if(elapsedTime.time()<3)subsystemGate.SetPowerGate(-0.3);
        subsystemGate.SetPowerGate(OpenPower);

    }

    @Override
    public void end(boolean interrupted) {
        subsystemGate.SetPowerGate(ClosePower);
    }
}
