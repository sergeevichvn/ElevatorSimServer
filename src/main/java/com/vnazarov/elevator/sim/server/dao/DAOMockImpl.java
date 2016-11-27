package com.vnazarov.elevator.sim.server.dao;

import com.vnazarov.elevator.sim.server.Constants;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

//@Component
public class DAOMockImpl implements DAO {

    private Map<Integer, Integer> mapFloors;

    public DAOMockImpl() {
        initMap();
    }


    public boolean checkFloorStatus(int targetFloor, int direction) {
        return mapFloors.get(targetFloor) == direction ||
                mapFloors.get(targetFloor) == Constants.HOLD ||
                mapFloors.get(targetFloor) == Constants.UP_DOWN;
    }

    public void putFloorInQueue(int targetFloor, int direction) {
        mapFloors.put(targetFloor, direction);
    }

    @Override
    public int getFloorStatus(int floor) {
        return mapFloors.get(floor);
    }

    @Override
    public void resetFloor(int targetFloor, int direction) {
        if (mapFloors.get(targetFloor) == Constants.UP_DOWN) {
            mapFloors.put(targetFloor, Constants.UP_DOWN - direction);
        } else {
            mapFloors.put(targetFloor, Constants.NONE);
        }

    }

    private void initMap() {
        mapFloors = new HashMap<>();
        for (int i = Constants.MIN_FLOORS; i <= Constants.MAX_FLOORS; i++) {
            mapFloors.put(i, Constants.NONE);
        }
    }

}
