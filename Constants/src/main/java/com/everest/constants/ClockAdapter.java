package com.everest.constants;

import com.everest.CommandBased.definition.Clock;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.concurrent.TimeUnit;

public class ClockAdapter implements Clock {
    private final ElapsedTime elapsedTime;



    private final TimeUnit timeUnit;
    public ClockAdapter(ElapsedTime elapsedTime, TimeUnit timeUnit){
        this.elapsedTime = elapsedTime;
        this.timeUnit = timeUnit;
    }


    @Override
    public double getTime() {
        return elapsedTime.time(timeUnit);
    }

    @Override
    public void reset() {
        elapsedTime.reset();
    }
}
