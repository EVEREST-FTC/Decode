package com.everest.constants;

public enum EnumTeam {
    BLUE(0, 0),RED(1, 0);
    final int pipeline;
    final double offset;

    EnumTeam(int pipeline, double offset) {
        this.pipeline = pipeline;

        this.offset = offset;
    }
    public int getPipeline(){
        return pipeline;
    }
    public double getOffset(){ return offset;}
}
