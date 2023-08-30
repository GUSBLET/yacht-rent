package com.yachtrent.databaselayer.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import org.springframework.jdbc.core.JdbcTemplate;
public class DatabaseConnection {

    private final JdbcTemplate jdbcTemplate;

    public DatabaseConnection(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
}
