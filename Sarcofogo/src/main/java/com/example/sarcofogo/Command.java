package com.example.sarcofogo;



public class Command extends com.everest.CommandBased.definition.Command {

    private final SubsystemSarcofogo subsystemSarcofogo;

    private final double alvo;
    private final Moment moment;
    public Command(SubsystemSarcofogo subsystemSarcofogo, double alvo, Moment moment) {
        this.subsystemSarcofogo = subsystemSarcofogo;
        this.alvo = alvo;
        this.moment = moment;
        addRequirements(subsystemSarcofogo);

    }

    @Override
    public void execute() {
        subsystemSarcofogo.setPosition(alvo);
        subsystemSarcofogo.setMoment(moment);
    }

    @Override
    public void end(boolean interrupted) {
       subsystemSarcofogo.resetPosiiton();
    }

}