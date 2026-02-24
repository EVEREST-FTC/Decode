package com.everest.intake;

import static com.everest.constants.Constants.CameraConstants.largeIncrementDistance;
import static com.everest.constants.Constants.CameraConstants.shortIncrementDistance;
import static com.everest.constants.Constants.ControllerConstants.GAMEPAD_AIM_TRIGGER;
import static com.everest.constants.Constants.IntakeConstants.CLOSE_LAST_INTAKE_POWER;
import static com.everest.constants.Constants.IntakeConstants.INTAKE_POWER_L;
import static com.everest.constants.Constants.IntakeConstants.INTAKE_POWER_NORMAL;
import static com.everest.constants.Constants.IntakeConstants.LAST_INTAKE_POWER;

import com.everest.CommandBased.compositions.ParallelCommandGroup;
import com.everest.CommandBased.compositions.ParallelRaceGroup;
import com.everest.CommandBased.compositions.RepeatCommand;
import com.everest.CommandBased.compositions.SequentialCommandGroup;
import com.everest.CommandBased.definition.Command;
import com.everest.CommandBased.essentials.Trigger;
import com.everest.CommandBased.util.ConditionalCommand;
import com.everest.CommandBased.util.InstantCommand;
import com.everest.CommandBased.util.WaitCommand;
import com.everest.constants.Constants;
import com.everest.intake.Command.CommandIntake;
import com.everest.intake.Subsystem.SubsytemIntake;
import com.qualcomm.robotcore.hardware.Gamepad;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import lombok.Builder;

@Builder
public class IntakeContainer implements com.everest.constants.meta.RobotContainer {
    /// subsitema da intake
    private final SubsytemIntake subsytemIntake;

    /// entradas de informações vindo de outros sistemas
    private final Gamepad gamepad;
    private final BooleanSupplier hasArtifact;
    private final BooleanSupplier ArtifactComplete;
    private final BooleanSupplier artifactsConditionfor2;


    @Override
    public void mainRoutine() {
        /// normal do lançamento
        subsytemIntake.setDefaultCommand(
                new CommandIntake(subsytemIntake,()-> INTAKE_POWER_NORMAL));
        /// parada do intake no final do end-game
        new Trigger(()->gamepad.y).toggleOnTrue(
                new Command() {
                    @Override
                    public void initialize() {
                        subsytemIntake.setActive(false);
                    }
                }.finalmente(()-> subsytemIntake.setActive(true))
        );
        /// comando para reverter a velociade do intake
      //  new Trigger(()->gamepad.left_stick_button).whileTrue(new CommandIntake(subsytemIntake,()->-INTAKE_POWER_NORMAL));

        /// parada pra lançamento
        new Trigger(ArtifactComplete)
                .whileTrue(new CommandIntake(subsytemIntake, ()->0));

       /* new Trigger(artifactsConditionfor2)
                .whileTrue(new CommandIntake(subsytemIntake, ()->0.025));*/
        /*new Trigger(()->gamepad.left_trigger_pressed && !ArtifactComplete.getAsBoolean())
                .whileTrue(new CommandIntake(subsytemIntake, ()->INTAKE_POWER_L));*/

        /*new Trigger(()->hasArtifact.getAsBoolean() && !ArtifactComplete.getAsBoolean())
                .whileTrue(new CommandIntake(subsytemIntake, ()->0.015));*/
        /*
        new Trigger(()->gamepad.left_trigger> GAMEPAD_AIM_TRIGGER&&hasArtifact.getAsBoolean())
                .whileTrue(new CommandIntake(subsytemIntake, ()->0));*/
    }
}
