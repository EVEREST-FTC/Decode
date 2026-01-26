package com.example.sarcofogo;

import static com.everest.constants.Constants.ControllerConstants.GAMEPAD_AIM_TRIGGER;
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
    private final BooleanSupplier artifactMoment;
    @Override
    public void mainRoutine() {
       /*subsystemSarcofogo.setDefaultCommand(
                new SelectCommand<>(
                Map.ofEntries(
                        Map.entry(Moment.KEEP, new Command(subsystemSarcofogo,
                                SarcofogoInitialPosition, Moment.KEEP)),
                        Map.entry(Moment.SEND, new Command(subsystemSarcofogo,50, Moment.SEND).ateQUe(()->!artifactMoment.getAsBoolean())),
                        Map.entry(Moment.UNACTIVE, new Command(subsystemSarcofogo,0, Moment.UNACTIVE))
                ),
                ()->Moment.select(artifactMoment.getAsBoolean()&&
                        (gamepad1.left_trigger>GAMEPAD_AIM_TRIGGER||gamepad2.left_trigger>GAMEPAD_AIM_TRIGGER))
        ));*/
        subsystemSarcofogo.setDefaultCommand(
                new SelectCommand<>(
                        Map.ofEntries(
                                Map.entry(Moment.KEEP, new Command(subsystemSarcofogo,
                                        SarcofogoInitialPosition, Moment.KEEP)),
                                Map.entry(Moment.SEND, new Command(subsystemSarcofogo,50, Moment.SEND).ateQUe(()->!artifactMoment.getAsBoolean())),
                                Map.entry(Moment.UNACTIVE, new Command(subsystemSarcofogo,0, Moment.UNACTIVE))
                        ),
                        ()->{
                            if(subsystemSarcofogo.getMoment().equals(Moment.UNACTIVE)) return Moment.UNACTIVE;
                            return Moment.select(artifactMoment.getAsBoolean()&&
                                    (gamepad1.left_trigger>GAMEPAD_AIM_TRIGGER||gamepad2.left_trigger>GAMEPAD_AIM_TRIGGER));
                        }
                ));

        new Trigger(()->gamepad1.left_trigger>GAMEPAD_AIM_TRIGGER)
                .or(()->gamepad2.left_trigger>GAMEPAD_AIM_TRIGGER).onFalse(new InstantCommand(subsystemSarcofogo::resetmemore));
        new Trigger(()->gamepad1.x)
                .or(()->gamepad2.x).toggleOnTrue(new RepeatCommand(new com.everest.CommandBased.definition.Command(){
                    @Override
                    public void initialize() {
                        subsystemSarcofogo.setMoment(Moment.ACTIVE);
                    }
                    }).finalmente((()->subsystemSarcofogo.setMoment(Moment.UNACTIVE))));
        flagSubsystem.setDefaultCommand(
                new CommandBandeira(flagSubsystem, 0)
        );
        new Trigger(()->ArtifactComplete.get()==2).whileTrue(
                new CommandBandeira(flagSubsystem,45)
        );
        new Trigger(()->ArtifactComplete.get()==3).whileTrue(
                new CommandBandeira(flagSubsystem,90)
        );
    }
}
