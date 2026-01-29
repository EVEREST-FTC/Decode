package com.everest.constants;

import com.qualcomm.robotcore.util.ElapsedTime;

import lombok.Getter;

public class PID {
    private final double KP;
    private final double KI;
    private final double KD;

    private final ElapsedTime timer;
    @Getter
    private double sum;
    private double lastTime;
    private double lastErro;
    private double distance;


    public PID(double kp, double ki, double kd) {
        KP = kp;
        KI = ki;
        KD = kd;
        this.timer = new ElapsedTime();
    }
    public void reset(){
        sum = 0;
        lastErro = 0;
        lastTime = 0;
        timer.reset();
    }

    public double max_limiter(double valor){
        if (Math.abs(valor) > Constants.GyroConstants.PID_MAX)
            return Constants.GyroConstants.PID_MAX;
        else
            return valor;

    }

    public double calculate(double target, double measurement, double Distance){
        double error = (target-measurement);
        double dt = timer.time() - lastTime;
        double derro = error - lastErro;
        double derivativo = 0;
        distance = Distance;
        if(Math.abs(error)<Constants.GyroConstants.iRange) {
            sum += error * dt;
            derivativo = derro/dt;
        }

        lastTime = timer.time();
        lastErro = error;
        return max_limiter(error*KP+sum*KI+derivativo*KD);
    }
    public double getError(){
        return lastErro;
    }
    public boolean atSetpoint(){
        if (lastErro == 0)
            return false;
        else if (distance < Constants.CameraConstants.shortIncrementDistance)
            return Math.abs(getError())< Constants.GyroConstants.SHORT_ADMISSIBLE_ERROR;
        else
            return Math.abs(getError())< Constants.GyroConstants.ADMISSIBLE_ERROR;
    }
}
