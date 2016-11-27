package com.vnazarov.elevator.sim.server.dao;

public interface DAO {
    boolean checkFloorStatus(int targetFloor, int direction);
    void resetFloor(int targetFloor, int direction);
    void putFloorInQueue(int targetFloor, int direction);
    int getFloorStatus(int floor);
}
