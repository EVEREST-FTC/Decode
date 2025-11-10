package com.everest.outtake.command;

import com.everest.CommandBased.definition.Command;

import com.everest.outtake.subsystem.SubsystemCalibrator;

public class CalibratorCommand extends Command {

    private SubsystemCalibrator SevoCalibrador;
    private double alvo;


    public CalibratorCommand(SubsystemCalibrator SevoCalibrador, double alvo) {
        this.SevoCalibrador = SevoCalibrador;
        this.alvo = alvo;
        addRequirements(SevoCalibrador);

    }


    @Override
    public void execute() {
        SevoCalibrador.setPositionL(alvo);

    }

    @Override
    public void end(boolean interrupted) {

        SevoCalibrador.setPositionL(45);
    }

}
