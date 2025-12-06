package com.everest.constants.meta;

public interface RobotContainer {
    default void defineMainRoutine(){
        states();
        mainRoutine();
    }
    default void states(){};
    void mainRoutine();
}
