package com.example.chassi.command;

import com.everest.CommandBased.definition.Command;
import com.example.chassi.MecanumDrive;
public class UpRobot extends Command {
    private final MecanumDrive chassis;
    ///  este  comando atua no end game com o objetivo de lenvantar o robo usando um motor com redução de 125:1
    public UpRobot(MecanumDrive chassis) {
        this.chassis = chassis;
        addRequirements(chassis);
    }


        @Override
        public void execute() {
        ///  na execução o comando usa o metodo do subsistema com o cauculo de reduções para o angulo de maior elevação
            chassis.setPositionElevator(90);}

        @Override
        ///  na finalização o motor volta para posição inicial
        public void end(boolean interrupted) {
            chassis.setPositionElevator(0);
    }

}
