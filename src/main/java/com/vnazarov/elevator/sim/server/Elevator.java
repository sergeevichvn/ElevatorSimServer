package com.vnazarov.elevator.sim.server;

import com.vnazarov.elevator.sim.server.dao.DAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Elevator {

    private int currentFloor;
    private int direction;
    private DAO dao;
    private int targetFloor = -1;


    @Autowired
    public Elevator(DAO dao) {
        this.direction = Constants.HOLD;
        this.currentFloor = Constants.DEFAULT_FLOOR;
        this.dao = dao;
    }

    public void goUp(int floor) {
        System.out.println("[Вызов] вверх с этажа "+floor);
        dao.putFloorInQueue(floor, Constants.UP);
        run(floor);
    }

    public void goDown(int floor) {
        System.out.println("[Вызов] вниз с этажа "+floor);
        dao.putFloorInQueue(floor, Constants.DOWN);
        run(floor);
    }

    public void stand(int floor) {
        System.out.println("[Вызов] остановки на этаже "+floor);
        dao.putFloorInQueue(floor, Constants.HOLD);
        run(floor);
    }

    private void run(int floor) {
        if (direction == Constants.HOLD) {
            synchronized (this) {
                if (targetFloor == -1) {
                    targetFloor = floor;
                }
            }
            System.out.println("Сейчас лифт находится на " + currentFloor + " этаже.");
            go();
        }
    }

    private void go() {
        if (direction == Constants.HOLD) {
            initDirection(); //определяем направление
        } else {
            calculateDirection(); //проверяем очередь
        }
        if (direction!=Constants.HOLD) {
            int nextFloor = getNextFloor();

            if (dao.checkFloorStatus(nextFloor, direction) ||
                    isLastFloor(nextFloor)) {
                move();
                stop();
            } else {
                move();
            }
            go();
        }
    }

    private boolean isLastFloor(int floor) {
        if (direction==Constants.UP) {
            if (floor==Constants.MAX_FLOORS ||
                    !checkQueueInDirection(floor + 1, direction)) {
                return true;
            }
        } else {
            if (floor==Constants.MIN_FLOORS ||
                    !checkQueueInDirection(floor - 1, direction)) {
                return true;
            }
        }
        return false;
    }


    private void calculateDirection() {
        if (checkQueueInDirection(currentFloor, direction)) { //есть ли в текущем направлении еще клиенты
        } else if (checkQueueInDirection(currentFloor, Constants.UP_DOWN - direction)) { //если нет, то проверяем в другом направление
            direction = Constants.UP_DOWN - direction;
        } else { //если никого нет, то стоим и ничего не делаем
            direction = Constants.HOLD;
            targetFloor = -1;
        }
    }

    //проверяем очередь в выбраном направление
    private boolean checkQueueInDirection(int fromFloor, int direction) {
        if (direction == Constants.DOWN) {
            for (int i = fromFloor; i >= Constants.MIN_FLOORS; i--) {
                if (dao.getFloorStatus(i) != Constants.NONE) {
                    return true;
                }
            }
        } else if (direction == Constants.UP) {
            for (int i = fromFloor; i <= Constants.MAX_FLOORS; i++) {
                if (dao.getFloorStatus(i) != Constants.NONE) {
                    return true;
                }
            }
        }
        return false;
    }

    //определяем напраавление лифта
    private void initDirection() {
        if (currentFloor > targetFloor) {
            direction = Constants.DOWN;
        } else if (currentFloor < targetFloor) {
            direction = Constants.UP;
        } else {
            direction = Constants.HOLD;
        }
    }



    private void stop() {
        try {
            System.out.println("Останавливаемся на " + currentFloor);
            Thread.sleep(Constants.STOP_TIME);
            dao.resetFloor(currentFloor, direction);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    private void move() {
        try {
            System.out.println("Проезжаем " + currentFloor + " этаж");
            Thread.sleep(Constants.WAY_TIME);
            changeCurrentFloor();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    private void changeCurrentFloor() {
        if (direction == Constants.DOWN && currentFloor >= Constants.MIN_FLOORS) {
            currentFloor--;
        } else if (direction == Constants.UP && currentFloor <= Constants.MAX_FLOORS) {
            currentFloor++;
        }
    }

    private int getNextFloor() {
        if (direction == Constants.DOWN && currentFloor > Constants.MIN_FLOORS) {
            return currentFloor - 1;
        } else if (direction == Constants.UP && currentFloor < Constants.MAX_FLOORS) {
            return currentFloor + 1;
        } else {
            return currentFloor;
        }
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public int getDirection() {
        return direction;
    }
}
