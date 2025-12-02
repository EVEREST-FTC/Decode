package com.example.gate;



public class Command extends com.everest.CommandBased.definition.Command {

    private final SubsystemGate subsystemGate;

    private final double alvo;
    public Command(SubsystemGate subsystemGate, double alvo) {
        this.subsystemGate = subsystemGate;
        this.alvo = alvo;
        addRequirements(subsystemGate);

    }

    @Override
    public void execute() {
        subsystemGate.setPositionGate(alvo);

    }

}