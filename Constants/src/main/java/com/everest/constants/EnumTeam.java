package com.everest.constants;

public enum EnumTeam {
    BLUE(0,-90, Constants.PID_INCREMENT_BLUE,Constants.PID_SHORTINCREMENT_BLUE),
    RED(1,90, Constants.PID_INCREMENT_RED,Constants.PID_SHORTINCREMENT_RED);
    final int pipeline;
    final double offset;
    final double increment;

    final double shortIncrement;

    EnumTeam(int pipeline, double offset, double increment, double shortIncrement) {
        this.pipeline = pipeline;

        this.offset = offset;
        this.increment = increment;
        this.shortIncrement = shortIncrement;

    }
    public int getPipeline(){
        return pipeline;
    }
    public double getOffset(){ return offset;}

    public double getIncrement(){
        return increment;
    }
    public double getShortIncrement(){
        return shortIncrement;
    }
}
