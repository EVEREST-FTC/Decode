package com.example.limelightcentral;

import com.everest.CommandBased.definition.CommandScheduler;
import com.everest.CommandBased.essentials.SubsystemBase;
import com.everest.constants.Constants;
import com.everest.constants.meta.EnumTeam;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import java.util.List;
import java.util.function.DoubleSupplier;

public class Subsystem extends SubsystemBase {
    Limelight3A limelight3A;
    final Telemetry telemetry;
    final EnumTeam team;
    final DoubleSupplier angle;

    public Subsystem(HardwareMap hardwareMap, Telemetry telemetry, EnumTeam team, DoubleSupplier angle) {
        limelight3A = hardwareMap.get(Limelight3A.class,"Lime3A");
        this.telemetry = telemetry;
        this.team = team;
        this.angle = angle;
        limelight3A.start();
        telemetry.setMsTransmissionInterval(11);

        limelight3A.pipelineSwitch(team.getPipeline());


        CommandScheduler.getInstance().registerSubsystem(this);
    }
    public double getfrontal(){
        double relativeHeight = Constants.CameraConstants.TAG_RELATIVE_HEIGHT;
        double angle = getTy();
        return relativeHeight/Math.tan(Math.toRadians(angle));
    }

    public double getTy(){
        LLResult latestResult = limelight3A.getLatestResult();
        if(!isValid()) return 0.0;
        return latestResult.getTy() + Constants.CameraConstants.initialAngle;
    }
    public boolean isValid(){
        LLResult latestResult = limelight3A.getLatestResult();
        return latestResult.isValid();
    }
    public double getTx(){
        LLResult latestResult = limelight3A.getLatestResult();
        if(!isValid()) return 0.0;
        return latestResult.getTx();
    }

    public int getTagId(){
        List<LLResultTypes.FiducialResult> Tags = limelight3A.getLatestResult().getFiducialResults();
            if (Tags.isEmpty())
                return 0;
            return Tags.get(0).getFiducialId();

    }
    public void pipelineSwitch(int id){
        limelight3A.pipelineSwitch(id);
    }

    @Override
    public void periodic() {
        limelight3A.updateRobotOrientation(angle.getAsDouble());
        telemetry.addData("is valid", isValid());
        telemetry.addData("distance", getfrontal());
        telemetry.addData("tx", getTx());


    }
}
