package com.everest.constants;

public enum EnumTeam {
    BLUE(0,90, Constants.PID_INCREMENT_BLUE),
    RED(1,-90, Constants.PID_INCREMENT_RED);
    final int pipeline;
    final double offset;
    final double increment;

    EnumTeam(int pipeline, double offset, double increment) {
        this.pipeline = pipeline;

        this.offset = offset;
        this.increment = increment;
    }
    public int getPipeline(){
        return pipeline;
    }
    public double getOffset(){ return offset;}

    public double getIncrement(){
        return increment;
    }
}
