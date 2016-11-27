package com.vnazarov.elevator.sim.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ElevatorService {

    @Autowired
    private Elevator elevator;

    @RequestMapping("/status")
    public Elevator getCurrentFloor() {
        return elevator;
    }

    @RequestMapping("/up")
    public void goUp(@RequestParam(value = "floor") int floor) {
        if (floor >= Constants.MIN_FLOORS && floor <= Constants.MAX_FLOORS)
            elevator.goUp(floor);
    }

    @RequestMapping("/down")
    public void goDown(@RequestParam(value = "floor") int floor) {
        if (floor >= Constants.MIN_FLOORS && floor <= Constants.MAX_FLOORS)
            elevator.goDown(floor);
    }

    @RequestMapping("/stand")
    public void stand(@RequestParam(value = "floor") int floor){
        if (floor >= Constants.MIN_FLOORS && floor <= Constants.MAX_FLOORS)
            elevator.goUp(floor);
    }

}
