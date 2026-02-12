package com.example.gate;



public class Command extends com.everest.CommandBased.definition.Command {
    private final SubsystemGate subsystemGate;
    private final double OpenPower,ClosePower;
    public Command(SubsystemGate subsystemGate, double OpenPower, double closePower) {
        this.subsystemGate = subsystemGate;
        this.OpenPower = OpenPower;
        ClosePower = closePower;
        addRequirements(subsystemGate);

    }

    @Override
    public void execute() {
        subsystemGate.SetPowerGate(OpenPower);

    }

    @Override
    public void end(boolean interrupted) {
        subsystemGate.SetPowerGate(ClosePower);
    }
}