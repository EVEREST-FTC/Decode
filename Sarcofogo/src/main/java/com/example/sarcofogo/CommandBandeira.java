package com.example.sarcofogo;



public class CommandBandeira extends com.everest.CommandBased.definition.Command {

    private final FlagSubsystem subsystemSarcofogo;

    private final double alvo;
    public CommandBandeira(FlagSubsystem subsystemSarcofogo, double alvo) {
        this.subsystemSarcofogo = subsystemSarcofogo;
        this.alvo = alvo;
        addRequirements(subsystemSarcofogo);

    }

    @Override
    public void execute() {
        subsystemSarcofogo.setPositionbandeira(alvo);

    }

    @Override
    public void end(boolean interrupted) {
        subsystemSarcofogo.setPositionbandeira(0);

    }

}