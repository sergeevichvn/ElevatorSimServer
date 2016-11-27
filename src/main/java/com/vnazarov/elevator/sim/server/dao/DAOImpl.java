package com.vnazarov.elevator.sim.server.dao;

import com.vnazarov.elevator.sim.server.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.*;

@Component
public class DAOImpl implements DAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean checkFloorStatus(int targetFloor, int direction) {
        int floorDirection = jdbcTemplate.queryForObject(Constants.SQL_FIND, new Object[]{targetFloor}, Integer.class);
        return floorDirection == direction ||
                floorDirection == Constants.HOLD ||
                floorDirection == Constants.UP_DOWN;
    }

    @Override
    public void resetFloor(int targetFloor, int direction) {
        int floorDirection = jdbcTemplate.queryForObject(Constants.SQL_FIND, new Object[]{targetFloor}, Integer.class);
        if (floorDirection == Constants.UP_DOWN) {
            jdbcTemplate.update(Constants.SQL_UPDATE, new Object[]{Constants.UP_DOWN - direction, targetFloor}, new int[]{Types.INTEGER, Types.INTEGER});
        } else {
            jdbcTemplate.update(Constants.SQL_UPDATE, new Object[]{Constants.NONE, targetFloor}, new int[]{Types.INTEGER, Types.INTEGER});
        }
    }

    @Override
    public void putFloorInQueue(int targetFloor, int direction) {
        jdbcTemplate.update(Constants.SQL_UPDATE, new Object[]{direction, targetFloor}, new int[]{Types.INTEGER, Types.INTEGER});
    }

    @Override
    public int getFloorStatus(int floor) {
        return jdbcTemplate.queryForObject(Constants.SQL_FIND, new Object[]{floor}, Integer.class);
    }

    @PostConstruct
    private void initDataBase() {
        jdbcTemplate.execute(Constants.INIT_DATABASE);
        for (int i = Constants.MIN_FLOORS; i <= Constants.MAX_FLOORS; i++) {
            jdbcTemplate.update(Constants.INIT_FLOORS, new Object[]{i}, new int[]{Types.INTEGER});
        }
    }
}
