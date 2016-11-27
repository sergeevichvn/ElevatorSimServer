package com.vnazarov.elevator.sim.server;

public final class Constants {
    public static final int UP = 1;
    public static final int DOWN = 2;
    public static final int UP_DOWN = 3; //если два клиента еду в разные направления с одного этажа
    public static final int HOLD = 0; //для лифта - стоим или этаж на котором нужно остановиться
    public static final int NONE = -1; //для этажа который пропускаем
    public static final long STOP_TIME = 10000; //имитируем ожидание лифта
    public static final long WAY_TIME = 4200; //имитирвем движение лифта 1 этаж
    public static final int MAX_FLOORS = 7;
    public static final int MIN_FLOORS = 1;
    public static final int DEFAULT_FLOOR = 7;

    public static final String TABLE_NAME = "Floors";
    public static final String ID_COLUMN = "id";
    public static final String STATUS_COLUMN = "status";
    public static final String SQL_FIND = "SELECT " + STATUS_COLUMN + " FROM " + TABLE_NAME + " WHERE " + ID_COLUMN + " = ?;";
    public static final String SQL_UPDATE = "UPDATE " + TABLE_NAME + " SET " + STATUS_COLUMN
            + " = ? WHERE " + ID_COLUMN + " = ?";
    public static final String INIT_DATABASE = "CREATE TABLE IF NOT EXISTS " + Constants.TABLE_NAME
            + "(" + Constants.ID_COLUMN + " INT PRIMARY KEY AUTO_INCREMENT,"
            + Constants.STATUS_COLUMN + " INT NOT NULL);";
    public static final String INIT_FLOORS = "INSERT INTO " + Constants.TABLE_NAME
            + " (" + Constants.ID_COLUMN + ", " + Constants.STATUS_COLUMN + ") "
            + "VALUES( ?, " + Constants.NONE + ");";

}
