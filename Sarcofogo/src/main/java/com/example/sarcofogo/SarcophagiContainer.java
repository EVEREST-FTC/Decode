package com.example.sarcofogo;

import static com.everest.constants.Constants.ControllerConstants.GAMEPAD_AIM_TRIGGER;
import static com.everest.constants.Constants.SarcofagoConstants.SARCOPHAGI_SEND_POSITION;
import static com.everest.constants.Constants.SarcofagoConstants.SarcofogoInitialPosition;

import com.everest.CommandBased.compositions.RepeatCommand;
import com.everest.CommandBased.compositions.SelectCommand;
import com.everest.CommandBased.essentials.Trigger;
import com.everest.CommandBased.util.InstantCommand;
import com.qualcomm.robotcore.hardware.Gamepad;
import java.util.Map;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import lombok.Builder;

@Builder
public class SarcophagiContainer implements com.everest.constants.meta.RobotContainer {
    private final Gamepad gamepad1;
    private final Gamepad gamepad2;
    private final SubsystemSarcofogo subsystemSarcofogo;
    private final FlagSubsystem flagSubsystem;
    private final BooleanSupplier hasArtifact;
    private final Supplier<Integer> ArtifactComplete;
    public final BooleanSupplier translationalSetpoint;
    private final BooleanSupplier artifactMoment;
    @Override
    public void mainRoutine() {
        /// maquina de estado para ativação no momento do sarcofogo de acordo com o padrão identificado no autonomo
        subsystemSarcofogo.setDefaultCommand(
                new SelectCommand<>(
                        Map.ofEntries(
                                Map.entry(Moment.KEEP, new Command(subsystemSarcofogo,
                                        SarcofogoInitialPosition, Moment.KEEP)),
                                Map.entry(Moment.SEND, new Command(subsystemSarcofogo,SARCOPHAGI_SEND_POSITION, Moment.SEND).ateQUe(()->!artifactMoment.getAsBoolean())),
                                Map.entry(Moment.UNACTIVE, new Command(subsystemSarcofogo,0, Moment.UNACTIVE))
                        ),
                        ()->{
                            if(subsystemSarcofogo.getMoment().equals(Moment.UNACTIVE)) return Moment.UNACTIVE;
                            return Moment.select(artifactMoment.getAsBoolean()&&
                                    (gamepad1.left_trigger>GAMEPAD_AIM_TRIGGER||gamepad2.left_trigger>GAMEPAD_AIM_TRIGGER));
                        }
                ));
        /// comando a resetar a memoria do sensor do sarcofogo
        new Trigger(()->gamepad1.left_trigger>GAMEPAD_AIM_TRIGGER)
                .or(()->gamepad2.left_trigger>GAMEPAD_AIM_TRIGGER)
                .onFalse(new InstantCommand(subsystemSarcofogo::resetmemore));

        /// comando para a ativar o uso do sarcofogo no teleOperado
        new Trigger(()->gamepad1.x)
                .or(()->gamepad2.x).toggleOnTrue(new RepeatCommand(new com.everest.CommandBased.definition.Command(){
                    @Override
                    public void initialize() {
                        subsystemSarcofogo.setMoment(Moment.ACTIVE);
                    }
                    }).finalmente((()->subsystemSarcofogo.setMoment(Moment.UNACTIVE)))
                );

        /// commando padrão para deixar a bandeira abaixada
        flagSubsystem.setDefaultCommand(
                new CommandBandeira(flagSubsystem, 0)
        );
        /// comando para posicionar a bandeira a 45 graus quando tiver 2 artefatos no robo
        new Trigger(()->ArtifactComplete.get()==2).and(()->!gamepad1.left_trigger_pressed).whileTrue(
                new CommandBandeira(flagSubsystem,45)
        );
        /// comando para posicionar a bandeira a 90 graus quando o robo estiver completo
        new Trigger(()->ArtifactComplete.get()==3&&!gamepad1.left_trigger_pressed).or(()->translationalSetpoint.getAsBoolean()&&gamepad1.left_trigger_pressed).whileTrue(
                new CommandBandeira(flagSubsystem,90)
        );
        new Trigger(translationalSetpoint).and(()->gamepad1.left_trigger_pressed).whileTrue(
                new CommandBandeira(flagSubsystem,90)
        );
        new Trigger(()->!translationalSetpoint.getAsBoolean()).and(()->gamepad1.left_trigger_pressed).whileTrue(
                new CommandBandeira(flagSubsystem,110)
        );
    }
}
