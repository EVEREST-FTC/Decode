package com.example.sarcofogo;



public class Command extends com.everest.CommandBased.definition.Command {

    private final SubsystemSarcofogo subsystemSarcofogo;

    private final double alvo;
    public Command(SubsystemSarcofogo subsystemSarcofogo, double alvo) {
        this.subsystemSarcofogo = subsystemSarcofogo;
        this.alvo = alvo;
        addRequirements(subsystemSarcofogo);

    }

    @Override
    public void execute() {
        subsystemSarcofogo.setPositionGate(alvo);

    }

    @Override
    public void end(boolean interrupted) {
       subsystemSarcofogo.resetPosiiton();

    }

}