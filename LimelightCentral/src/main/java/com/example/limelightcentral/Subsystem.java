package com.example.limelightcentral;

import com.acmerobotics.roadrunner.Pose2d;
import com.everest.CommandBased.definition.CommandScheduler;
import com.everest.CommandBased.essentials.SubsystemBase;
import com.everest.constants.Constants;
import com.everest.constants.meta.EnumTeam;
import com.everest.constants.util.MathUtil;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

import java.util.List;
import java.util.Optional;
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

        telemetry.addData("distancia", this::getfrontal);
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
        return latestResult.getTy() + Constants.initialAngle;
    }
    public boolean isValid(){
        LLResult latestResult = limelight3A.getLatestResult();
        return latestResult.isValid();
    }
    public boolean shortzonelaunch(){
        return Math.abs(getfrontal()) < 1.5;
    }
    public double getTx(){
        LLResult latestResult = limelight3A.getLatestResult();
        if(!isValid()) return 0.0;
        return latestResult.getTx();
    }

    public double getIncrementById(){
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
    public int getTagId(){
        List<LLResultTypes.FiducialResult> Tags = limelight3A.getLatestResult().getFiducialResults();
            if ( Tags.isEmpty())
                return 0;
            return Tags.get(0).getFiducialId();

    }

    public Optional<Pose3D> getBotPose(){
        List<LLResultTypes.FiducialResult> tags = limelight3A.getLatestResult().getFiducialResults();
        if(tags.isEmpty()) return Optional.empty();
        return Optional.of(
                tags.get(0).getCameraPoseTargetSpace()
        );
    }
    public Optional<Pose2d> getBotPoseInches(){
        Optional<Pose3D> pose3DOptional = getBotPose();
        if(!pose3DOptional.isPresent()) return Optional.empty();
        Pose3D pose3D = pose3DOptional.get();
        return Optional.of(
                new Pose2d(
                        MathUtil.metersToInches(pose3D.getPosition().x),
                        MathUtil.metersToInches(pose3D.getPosition().y),
                        angle.getAsDouble()
                )
        );
    }
    public void pipelineSwitch(int id){
        limelight3A.pipelineSwitch(id);
    }

    @Override
    public void periodic() {
        limelight3A.updateRobotOrientation(angle.getAsDouble());
        LLResult llResult = limelight3A.getLatestResult();
        List<LLResultTypes.FiducialResult> Tags = llResult.getFiducialResults();
        Tags.forEach(
                tag->telemetry.addLine(String.valueOf(tag.getFiducialId()))
        );

        Optional<Pose2d> poseOptional = getBotPoseInches();
        if(!poseOptional.isPresent()) return;
        Pose2d pose2d = poseOptional.get();
        telemetry.addData("limelight3A-getIdtag", getIncrementById());
    }
}
