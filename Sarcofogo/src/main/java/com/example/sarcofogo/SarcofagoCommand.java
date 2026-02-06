package com.example.sarcofogo;

import com.everest.CommandBased.definition.Command;

public class SarcofagoCommand extends Command {
    SubsystemSarcofogo sarcofogo;
    final double position;

    public SarcofagoCommand(SubsystemSarcofogo sarcofogo, double position) {
        this.sarcofogo = sarcofogo;
        this.position = position;
        addRequirements(sarcofogo);
    }

    @Override
    public void execute() {
        sarcofogo.setPosition(position);
    }
}
