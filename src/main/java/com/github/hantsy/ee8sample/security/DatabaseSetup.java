/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.hantsy.ee8sample.security;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import javax.sql.DataSource;

/**
 *
 * @author hantsy
 */
//@DataSourceDefinition(
//        // global to circumvent https://java.net/jira/browse/GLASSFISH-21447
//        name = "java:global/MyDS",
//        className = "org.h2.jdbcx.JdbcDataSource",
//        // :mem:test would be better, but TomEE insists on this being a file
//        url = "jdbc:h2:mem:test;DB_CLOSE_ON_EXIT=FALSE"
//)
@Singleton
@Startup
public class DatabaseSetup {

    //@Resource(lookup = "java:global/MyDS")
    @Resource// use default DataSource instead
    private DataSource dataSource;

    @Inject
    private Pbkdf2PasswordHash passwordHash;

    @Inject
    Logger LOG;

    @PostConstruct
    public void init() {

        LOG.info("initializing database...");

        Map<String, String> parameters = new HashMap<>();
        parameters.put("Pbkdf2PasswordHash.Iterations", "3072");
        parameters.put("Pbkdf2PasswordHash.Algorithm", "PBKDF2WithHmacSHA512");
        parameters.put("Pbkdf2PasswordHash.SaltSizeBytes", "64");
        passwordHash.initialize(parameters);

//        executeUpdate(dataSource, "DROP TABLE IF EXISTS caller");
//        executeUpdate(dataSource, "DROP TABLE IF EXISTS caller_groups");
        executeUpdate(dataSource, "DROP TABLE caller");
        executeUpdate(dataSource, "DROP TABLE caller_groups");

//         executeUpdate(dataSource, "CREATE TABLE IF NOT EXISTS caller(name VARCHAR(64) PRIMARY KEY, password VARCHAR(255))");
//        executeUpdate(dataSource, "CREATE TABLE IF NOT EXISTS caller_groups(caller_name VARCHAR(64), group_name VARCHAR(64))");
        executeUpdate(dataSource, "CREATE TABLE caller(name VARCHAR(64) PRIMARY KEY, password VARCHAR(255))");
        executeUpdate(dataSource, "CREATE TABLE caller_groups(caller_name VARCHAR(64), group_name VARCHAR(64))");

        executeUpdate(dataSource, "INSERT INTO caller VALUES('user', '" + passwordHash.generate("password".toCharArray()) + "')");
        executeUpdate(dataSource, "INSERT INTO caller VALUES('admin', '" + passwordHash.generate("password".toCharArray()) + "')");

        executeUpdate(dataSource, "INSERT INTO caller_groups VALUES('user', 'ROLE_USER')");
        // executeUpdate(dataSource, "INSERT INTO caller_groups VALUES('user', 'ROLE_ADMIN')");

        executeUpdate(dataSource, "INSERT INTO caller_groups VALUES('admin', 'ROLE_USER')");
        executeUpdate(dataSource, "INSERT INTO caller_groups VALUES('admin', 'ROLE_ADMIN')");

    }

    @PreDestroy
    public void destroy() {
        try {
//            executeUpdate(dataSource, "DROP TABLE IF EXISTS caller");
//            executeUpdate(dataSource, "DROP TABLE IF EXISTS caller_groups");

            executeUpdate(dataSource, "DROP TABLE  caller");
            executeUpdate(dataSource, "DROP TABLE  caller_groups");
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "error drop tables:." + e.getMessage());

        }
    }

    private void executeUpdate(DataSource dataSource, String query) {
        LOG.log(Level.INFO, "executing query:." + query);
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "error executed query:." + e.getMessage());
        }
    }

}
