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

public class Subsystem extends SubsystemBase {
    Limelight3A limelight3A;
    final Telemetry telemetry;

    final EnumTeam team;

    public Subsystem(HardwareMap hardwareMap, Telemetry telemetry, EnumTeam team) {
        limelight3A = hardwareMap.get(Limelight3A.class,"Lime3A");
        this.telemetry = telemetry;
        this.team = team;
        limelight3A.start();
        telemetry.setMsTransmissionInterval(11);

        limelight3A.pipelineSwitch(team.getPipeline());


        CommandScheduler.getInstance().registerSubsystem(this);

        telemetry.addData("distancia", this::getfrontal);
        telemetry.addData("ty", this::getTy);
        telemetry.addData("tx", this::getTx);
    }
    public double getfrontal(){
        double relativeHeight = Constants.TAG_RELATIVE_HEIGHT;
        double angle = getTy();
        return relativeHeight/Math.tan(Math.toRadians(angle));
    }

    public double getTy(){
        LLResult latestResult = limelight3A.getLatestResult();
        if(!isValid()) return 0.0;
        return -latestResult.getTx();
    }
    public boolean isValid(){
        LLResult latestResult = limelight3A.getLatestResult();
        return latestResult.isValid();
    }
    public double getTx(){
        LLResult latestResult = limelight3A.getLatestResult();
        if(!isValid()) return 0.0;
        return latestResult.getTy();
    }
    public double getIdtag(){
        List<LLResultTypes.FiducialResult> Tags = limelight3A.getLatestResult().getFiducialResults();
        if ( Tags.isEmpty())
            return 0;
        int ID  = Tags.get(0).getFiducialId();
        if (ID == 20)
            return Constants.PID_INCREMENT_BLUE;
        else if (ID == 24)
            return Constants.PID_INCREMENT_RED;
        else
            return 0;
    }

    @Override
    public void periodic() {
        List<LLResultTypes.FiducialResult> Tags = limelight3A.getLatestResult().getFiducialResults();
        Tags.forEach(
                tag->telemetry.addLine(String.valueOf(tag.getFiducialId()))
        );
        telemetry.addData("getIdtag",getIdtag());
    }
}
