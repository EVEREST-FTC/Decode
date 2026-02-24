package com.example.chassi;

import static com.everest.constants.Constants.ControllerConstants.CHASSIS_LIMIT_POWER_TURN;
import static com.everest.constants.Constants.ControllerConstants.CHASSIS_MIN_LIMIT_POWER_TURN;
import static com.everest.constants.Constants.ControllerConstants.GAMEPAD_AIM_TRIGGER;
import static com.everest.constants.Constants.ControllerConstants.CHASSIS_LIMIT_POWER;
import static com.everest.constants.Constants.ControllerConstants.GAMEPAD_AIM_TRIGGER;
import com.everest.CommandBased.essentials.Trigger;
import com.everest.CommandBased.util.InstantCommand;
import com.everest.constants.Constants;
import com.everest.constants.Constants.ControllerConstants;
import com.everest.constants.meta.EnumTeam;
import com.everest.constants.meta.RobotContainer;
import com.example.chassi.command.AlignToAngle;
import com.example.chassi.command.Drive;
import com.example.chassi.command.UpRobot;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Const;

import java.util.function.DoubleSupplier;

import lombok.Builder;

@Builder
///  é o container é o fica a senquencia logica  e utilização dos comando de acordo com as condições
public class ChassisContainer implements RobotContainer {
    /// subsitema do chassis
    private final MecanumDrive chassis;
    /// entradas de informações vindo de outros sistemas
    private final Gamepad gamepad1;
    private final Gamepad gamepad2;
    private final DoubleSupplier distance;
    private final EnumTeam team;
    private final DoubleSupplier target;


    public void mainRoutine(){
        /// ação de  resetar a orientação do robo
        new Trigger(()->gamepad1.start).or(()->gamepad1.start).whileTrue(
                new InstantCommand(chassis::resetIMU)
        );
        /// ação de levantar o robo
        new Trigger(()->gamepad1.y).or(()->gamepad2.y).toggleOnTrue(new UpRobot(chassis));

        /// ação com conjunto de comandos ativado no momento de lançamento
        new Trigger(()->gamepad1.left_trigger> GAMEPAD_AIM_TRIGGER).whileTrue(
                new AlignToAngle(chassis.telemetry, target, chassis,
                        distance,
                        chassis.getPid(),
                        team.getIncrement(),
                        team.getShortIncrement(),
                        team.getLargeIncrement()
                ).ateQUe(chassis::atSetpoint)
        );
        /// comando de diminuir a velocidade de movimento do robo para facilitar a precisão da coleta
        new Trigger(()->gamepad1.right_bumper).whileTrue(
                new Drive(
                        chassis,
                        () -> (chassis.DeadZone(gamepad1.right_stick_x+gamepad2.right_stick_x)+
                                Math.signum(gamepad1.right_stick_x+gamepad2.right_stick_x)*(gamepad1.right_trigger+gamepad2.right_trigger))*ControllerConstants.CHASSIS_LIMIT_POWER_TURN,
                        () -> chassis.DeadZone(gamepad1.left_stick_x+gamepad2.left_stick_x) * ControllerConstants.CHASSIS_REDUCTION,
                        () -> chassis.DeadZone(gamepad1.left_stick_y+gamepad2.left_stick_y) * ControllerConstants.CHASSIS_REDUCTION));
        /// comando padrão de movimento com velocidade vertical e horizaoltal maximo
        chassis.setDefaultCommand(
                new Drive(
                        chassis,
                        () -> (chassis.DeadZone(gamepad1.right_stick_x+gamepad2.right_stick_x)+
                                Math.signum(gamepad1.right_stick_x+gamepad2.right_stick_x)*(gamepad1.right_trigger+gamepad2.right_trigger))*ControllerConstants.CHASSIS_LIMIT_POWER_TURN,
                        () -> chassis.DeadZone(gamepad1.left_stick_x+gamepad2.left_stick_x) * ControllerConstants.CHASSIS_LIMIT_POWER,
                        () -> chassis.DeadZone(gamepad1.left_stick_y+gamepad2.left_stick_y) * ControllerConstants.CHASSIS_LIMIT_POWER));

        /// teste para posicionamento rapido e manual
        /*new Trigger(()->gamepad1.a).whileTrue(
                chassis.strafeToLinearHeading(
                        0,
                        0,
                        0,
                        60)
        );*/
    }

}
