package com.everest.plataform.command;


import com.everest.CommandBased.definition.Command;
import com.everest.plataform.subsystem.SubsystemCalibrator;

public class CalibratorCommand extends Command {

    private final SubsystemCalibrator outtakeSevoCalibrador;
    private final double alvo;


    public CalibratorCommand(SubsystemCalibrator outtakeSevoCalibrador, double alvo) {
        this.outtakeSevoCalibrador = outtakeSevoCalibrador;
        this.alvo = alvo;
        addRequirements(outtakeSevoCalibrador);

    }


    @Override
    public void execute() {
        outtakeSevoCalibrador.setPositionL(alvo);
    }

    @Override
    public void end(boolean interrupted) {

        outtakeSevoCalibrador.setPositionL(45);
    }

}
